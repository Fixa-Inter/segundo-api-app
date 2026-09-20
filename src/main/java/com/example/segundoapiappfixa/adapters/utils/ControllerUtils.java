package com.example.segundoapiappfixa.adapters.utils;

import com.example.segundoapiappfixa.auth.dto.AuthenticatedUser;
import org.springframework.security.core.Authentication;

public final class ControllerUtils {

    private ControllerUtils() { }

    public static Long usuarioId(Authentication authentication) {
        return ((AuthenticatedUser) authentication.getPrincipal()).id();
    }

    public static boolean isGestor(Authentication authentication) {
        return authentication
                .getAuthorities()
                .stream()
                .anyMatch(
                        authority -> "ROLE_GESTOR"
                                .equals(authority.getAuthority())
                );
    }
}
