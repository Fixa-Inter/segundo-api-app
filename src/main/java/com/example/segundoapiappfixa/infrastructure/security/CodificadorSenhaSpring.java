package com.example.segundoapiappfixa.infrastructure.security;

import com.example.segundoapiappfixa.domain.service.auth.CodificadorSenha;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CodificadorSenhaSpring implements CodificadorSenha {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String codificar(String senha) {
        return passwordEncoder.encode(senha);
    }
}
