package com.example.segundoapiappfixa.auth.service;

import com.example.segundoapiappfixa.auth.dto.LoginRequestDTO;
import com.example.segundoapiappfixa.auth.dto.LoginResponseDTO;
import com.example.segundoapiappfixa.auth.dto.RedefinirSenhaOutputDTO;
import com.example.segundoapiappfixa.auth.dto.RedefinirSenhaRequestDTO;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.model.RecuperarSenhaCodigo;
import com.example.segundoapiappfixa.domain.repository.PasswordTokenRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.security.JwtTokenProvider;
import com.example.segundoapiappfixa.infrastructure.external.EmailSender;
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
    private final PasswordTokenRepository passwordTokenRepository;
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

        RecuperarSenhaCodigo anterior = passwordTokenRepository.findActiveByUsuarioId(usuario.getId());

        // Desativa código ainda vigente
        if (anterior != null) {
            anterior.setEstaAtivo(false);
            passwordTokenRepository.save(anterior);
        }

        int codigo = ThreadLocalRandom.current().nextInt(100000, 1000000);
        long dataExpiracaoMilissegundos = 3 * 60 * 1000L;
        String nome = usuario.getNomeCompleto().split(" ")[0];

        RecuperarSenhaCodigo recuperacao = new RecuperarSenhaCodigo(
                null,
                usuario,
                codigo,
                new Date(System.currentTimeMillis() + dataExpiracaoMilissegundos),
                true
        );

        passwordTokenRepository.save(recuperacao);

        emailSender.enviarCodigo(
                usuario.getEmail(),
                nome,
                String.valueOf(codigo),
                String.format("%d minutos", dataExpiracaoMilissegundos / (60 * 1000L))
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

        RecuperarSenhaCodigo recuperacao = passwordTokenRepository.findActiveByUsuarioId(usuario.getId());

        return recuperacao != null
                && codigo != null
                && codigo.equals(recuperacao.getCodigo())
                && recuperacao.getDataExpiracao().after(new Date());
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

        RecuperarSenhaCodigo recuperacao = passwordTokenRepository.findActiveByUsuarioId(usuario.getId());
        recuperacao.setEstaAtivo(false);
        passwordTokenRepository.save(recuperacao);

        return "Nova senha registrada com sucesso!";
    }

}
