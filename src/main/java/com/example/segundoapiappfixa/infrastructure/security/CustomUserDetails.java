package com.example.segundoapiappfixa.infrastructure.security;

import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Locale;

@Getter
public class CustomUserDetails extends User {

    public CustomUserDetails(
            String email,
            String password,
            TipoAcesso tipoAcesso
    ) {
        super(
                email,
                password,
                List.of( new SimpleGrantedAuthority(String.format(
                        "ROLE_%s", tipoAcesso.getNome().toUpperCase())
                ))
        );
    }
}
