package com.example.segundoapiappfixa.infrastructure.swagger;

import com.example.segundoapiappfixa.adapters.controller.contract.PerfilControllerContract;
import com.example.segundoapiappfixa.adapters.dto.output.Usuario.UsuarioOutputDTO;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

@Tag(name = "Perfil", description = "Consulta dos dados do perfil autenticado.")
@SecurityRequirement(name = "bearerAuth")
public abstract class PerfilSwagger implements PerfilControllerContract {
    public static List<SwaggerEndpoint> endpoints() {
        return List.of(new SwaggerEndpoint("GET", "/api/v1/perfil", "Perfil", "Consultar perfil", "Consulta os dados do perfil autenticado."));
    }

    @Operation(summary = "Consultar perfil")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil encontrado", content = @Content(schema = @Schema(implementation = UsuarioOutputDTO.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public abstract ResponseEntity<UsuarioOutputDTO> detalhesPerfil(@Parameter(description = "Campos separados por vírgula", in = ParameterIn.QUERY) String campos, Authentication authentication);
}


