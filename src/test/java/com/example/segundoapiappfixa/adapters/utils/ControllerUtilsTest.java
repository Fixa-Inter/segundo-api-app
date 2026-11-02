package com.example.segundoapiappfixa.adapters.utils;

import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerUtilsTest {
    @Test
    void extraiCadaPermissaoConhecida() {
        for (TipoAcesso tipo : TipoAcesso.values()) {
            var auth = new UsernamePasswordAuthenticationToken("usuario", null,
                    List.of(new SimpleGrantedAuthority("ROLE_" + tipo.name())));
            assertEquals(tipo, ControllerUtils.tipoAcesso(auth));
        }
    }

    @Test
    void recusaPermissaoAusenteOuAmbigua() {
        var desconhecida = new UsernamePasswordAuthenticationToken("usuario", null,
                List.of(new SimpleGrantedAuthority("ROLE_DESCONHECIDA")));
        var ambigua = new UsernamePasswordAuthenticationToken("usuario", null,
                List.of(new SimpleGrantedAuthority("ROLE_GESTOR"), new SimpleGrantedAuthority("ROLE_TECNICO")));
        assertThrows(AccessDeniedException.class, () -> ControllerUtils.tipoAcesso(desconhecida));
        assertThrows(AccessDeniedException.class, () -> ControllerUtils.tipoAcesso(ambigua));
        assertThrows(AccessDeniedException.class, () -> ControllerUtils.tipoAcesso(null));
    }
}
