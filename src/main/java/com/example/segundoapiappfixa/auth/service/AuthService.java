package com.example.segundoapiappfixa.auth.service;

import com.example.segundoapiappfixa.auth.dto.LoginRequestDTO;
import com.example.segundoapiappfixa.auth.dto.LoginResponseDTO;
import com.example.segundoapiappfixa.auth.dto.RedefinirSenhaOutputDTO;
import com.example.segundoapiappfixa.auth.dto.RedefinirSenhaRequestDTO;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.model.RecuperarSenhaCodigo;
import com.example.segundoapiappfixa.domain.repository.RecuperarSenhaCodigoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.security.JwtTokenProvider;
import com.example.segundoapiappfixa.infrastructure.external.email.EmailSender;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
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
