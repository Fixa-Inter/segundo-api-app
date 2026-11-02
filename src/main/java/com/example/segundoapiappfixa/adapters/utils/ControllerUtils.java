package com.example.segundoapiappfixa.adapters.utils;

import com.example.segundoapiappfixa.auth.dto.AuthenticatedUser;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.AccessDeniedException;

public final class ControllerUtils {

    private ControllerUtils() { }

    public static Long usuarioId(Authentication authentication) {
        return ((AuthenticatedUser) authentication.getPrincipal()).id();
    }

    public static TipoAcesso tipoAcesso(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Usuário não autenticado");
        }

        var permissoes = authentication.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .filter(role -> role != null && role.startsWith("ROLE_"))
                .map(role -> {
                    try {
                        return TipoAcesso.valueOf(role.substring(5));
                    } catch (IllegalArgumentException ex) {
                        return null;
                    }
                })
                .filter(java.util.Objects::nonNull)
                .distinct()
                .toList();

        if (permissoes.size() != 1) {
            throw new AccessDeniedException("Tipo de acesso inválido");
        }

        return permissoes.get(0);
    }
}
