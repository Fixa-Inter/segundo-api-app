package com.example.segundoapiappfixa.adapters.controller.contract;

import com.example.segundoapiappfixa.adapters.dto.input.Usuario.UsuarioAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Usuario.UsuarioOutputDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface PerfilControllerContract {
    ResponseEntity<UsuarioOutputDTO> detalhesPerfil(String campos, Authentication authentication);

    ResponseEntity<UsuarioOutputDTO> atualizarPerfil(
            UsuarioAtualizarInputDTO dto,
            Authentication authentication
    );
}
