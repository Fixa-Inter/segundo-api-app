package com.example.segundoapiappfixa.auth.service;

import com.example.segundoapiappfixa.auth.dto.*;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.model.RecuperarSenhaCodigo;
import com.example.segundoapiappfixa.domain.repository.RecuperarSenhaCodigoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.domain.repository.UnidadeRepository;
import com.example.segundoapiappfixa.domain.model.Unidade;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.security.JwtTokenProvider;
import com.example.segundoapiappfixa.infrastructure.external.email.EmailSender;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import com.example.segundoapiappfixa.infrastructure.security.FirebaseTokenVerifier;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RecuperarSenhaCodigoRepository recuperarSenhaCodigoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final UnidadeRepository unidadeRepository;
    private final FirebaseTokenVerifier firebaseTokenVerifier;

    public LoginResponseDTO cadastrar(CadastroUsuarioRequestDTO dto) {
        String email = dto.email().trim().toLowerCase(Locale.ROOT);

        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new RegraProblemaException("exception.usuario.emailAlreadyExists");
        }

        if (dto.dataNascimento().isAfter(LocalDate.now().minusYears(12))) {
            throw new RegraProblemaException("validation.usuario.dataNascimento.minimumAge");
        }

        Unidade unidade = unidadeRepository.findById(dto.unidadeId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.unidade.notFound"));

        String dominio = unidade.getInstituicao().getDominioEmail().toLowerCase(Locale.ROOT);
        String dominioEmail = email.substring(email.indexOf('@') + 1);
        if (!dominio.equals(dominioEmail)) {
            throw new RegraProblemaException("validation.usuario.emailDomain.invalid");
        }

        Usuario usuario = new Usuario(
                null,
                unidade,
                dto.nomeCompleto().trim(),
                email,
                TipoAcesso.SOLICITANTE,
                passwordEncoder.encode(dto.senha()),
                dto.cargo().trim(),
                dto.dataNascimento(),
                LocalDateTime.now(),
                true,
                false,
                List.of(),
                null,
                null
        );

        Usuario salvo = usuarioRepository.save(usuario);
        String token = jwtTokenProvider.generateAccessToken(
                salvo.getId(),
                salvo.getEmail(),
                TipoAcesso.SOLICITANTE.name(),
                new Date()
        );

        return new LoginResponseDTO(salvo.getEmail(), token);
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {

        String email = dto.email();
        String senha = dto.senha();

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                email,
                                senha
                        )
                );

        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);

        if (usuario == null || !Boolean.TRUE.equals(usuario.getEstaAtivo())) {
            throw new DisabledException("Usuário inativo");
        }

        String role = authentication
                .getAuthorities()
                .iterator()
                .next()
                .getAuthority()
                .replace("ROLE_", "");

        String token = jwtTokenProvider.generateAccessToken(
                usuario.getId(),
                authentication.getName(),
                role,
                new Date()
        );

        return new LoginResponseDTO(
                authentication.getName(),
                token
        );
    }

    public GoogleLoginResponseDTO loginWithGoogle(GoogleLoginRequestDTO dto) {
        FirebaseToken firebaseToken = firebaseTokenVerifier.verify(dto.idToken());
        String email = firebaseToken.getEmail();

        if (!Boolean.TRUE.equals(firebaseToken.isEmailVerified())) {
            throw new RegraProblemaException("validation.auth.google.emailNotVerified");
        }

        email = email.trim().toLowerCase(Locale.ROOT);
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);

        if (usuario == null) {
            return GoogleLoginResponseDTO.registrationRequired(
                    email,
                    firebaseToken.getName()
            );
        }

        if (!Boolean.TRUE.equals(usuario.getEstaAtivo())) {
            throw new DisabledException("Usuário inativo");
        }

        String token = jwtTokenProvider.generateAccessToken(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getTipoAcesso().name(),
                new Date()
        );

        return GoogleLoginResponseDTO.authenticated(
                usuario.getEmail(),
                usuario.getNomeCompleto(),
                token
        );
    }

    public RedefinirSenhaOutputDTO esqueceuSenha(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("validation.usuario.required"));

        if (!Boolean.TRUE.equals(usuario.getEstaAtivo())) {
            throw new RegraProblemaException("validation.usuario.inativo");
        }

        int codigo = ThreadLocalRandom.current().nextInt(100000, 1000000);
        String nome = usuario.getNomeCompleto().split(" ")[0];

        recuperarSenhaCodigoRepository.deleteAll(usuario.getId());
        RecuperarSenhaCodigo recuperacao = new RecuperarSenhaCodigo(
                null,
                codigo,
                usuario.getId(),
                true
        );

        recuperarSenhaCodigoRepository.save(recuperacao);

        emailSender.enviarCodigo(
                usuario.getEmail(),
                nome,
                String.valueOf(codigo),
                "3 minutos"
        );

        return new RedefinirSenhaOutputDTO(
                nome,
                email,
                "Código enviado para o e-mail."
        );
    }

    public boolean validarCodigo(String email, Integer codigo) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RegraProblemaException("validation.codigo.invalido"));

        RecuperarSenhaCodigo recuperacao = recuperarSenhaCodigoRepository.findByUsuarioIdAndEstaAtivo(usuario.getId());

        return recuperacao != null
                && codigo != null
                && codigo.equals(recuperacao.getCodigo());
    }

    public String redefinirSenha(RedefinirSenhaRequestDTO dto) {

        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("validation.usuario.required"));

        if (!validarCodigo(dto.email(), dto.codigo())) {
            throw new RegraProblemaException("validation.codigo.invalido");
        }

        String novaSenhaHash = passwordEncoder.encode(dto.novaSenha());
        usuario.setSenhaHash(novaSenhaHash);
        usuarioRepository.save(usuario);

        recuperarSenhaCodigoRepository.deleteAll(usuario.getId());

        return "Nova senha registrada com sucesso!";
    }

}
