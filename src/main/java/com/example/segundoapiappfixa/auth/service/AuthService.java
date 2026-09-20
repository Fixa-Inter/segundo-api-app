package com.example.segundoapiappfixa.auth.service;

import com.example.segundoapiappfixa.auth.dto.LoginRequestDTO;
import com.example.segundoapiappfixa.auth.dto.LoginResponseDTO;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;

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
}
