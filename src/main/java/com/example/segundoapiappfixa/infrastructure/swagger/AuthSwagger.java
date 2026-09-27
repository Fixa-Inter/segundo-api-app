package com.example.segundoapiappfixa.infrastructure.swagger;

import com.example.segundoapiappfixa.auth.controller.contract.AuthControllerContract;
import com.example.segundoapiappfixa.auth.dto.LoginRequestDTO;
import com.example.segundoapiappfixa.auth.dto.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(
        name = "Autenticação",
        description = "Acesso e autenticação dos usuários da aplicação."
)
public abstract class AuthSwagger implements AuthControllerContract {

    public static List<SwaggerEndpoint> endpoints() {
        return List.of(
                new SwaggerEndpoint(
                        "POST",
                        "/api/v1/auth/login",
                        "Autenticação",
                        "Realizar login",
                        "Autentica o usuário e retorna um token JWT para acesso aos recursos protegidos."
                )
        );
    }

    @Override
    @Operation(
            summary = "Realizar login",
            description = "Autentica o usuário utilizando e-mail e senha e retorna um token JWT."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário autenticado com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = LoginResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "401", description = "E-mail ou senha inválidos"),
            @ApiResponse(responseCode = "400", description = "Dados de autenticação inválidos")
    })
    public abstract ResponseEntity<LoginResponseDTO> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credenciais do usuário",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = LoginRequestDTO.class)
                    )
            )
            LoginRequestDTO requestDTO
    );
}
