package com.example.segundoapiappfixa.infrastructure.security;

import com.example.segundoapiappfixa.domain.service.auth.AutenticadorCredenciais;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutenticadorCredenciaisSpring implements AutenticadorCredenciais {
    private final AuthenticationManager authenticationManager;

    @Override
    public String autenticar(String email, String senha) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, senha)).getName();
    }
}
