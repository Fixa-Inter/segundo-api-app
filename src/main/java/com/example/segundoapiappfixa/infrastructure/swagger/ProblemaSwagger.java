package com.example.segundoapiappfixa.infrastructure.swagger;

import com.example.segundoapiappfixa.adapters.controller.contract.ProblemaControllerContract;
import com.example.segundoapiappfixa.adapters.dto.input.Problema.*;
import com.example.segundoapiappfixa.adapters.dto.output.Problema.ProblemaOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosProblemaQueryParam;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

@Tag(name = "Problemas", description = "Registro e acompanhamento de problemas.")
@SecurityRequirement(name = "bearerAuth")
public abstract class ProblemaSwagger implements ProblemaControllerContract {
    public static List<SwaggerEndpoint> endpoints() {
        return List.of(new SwaggerEndpoint("GET", "/api/v1/solicitacoes", "Problemas", "Listar problemas", "Lista problemas com filtros e período de criação."),
                new SwaggerEndpoint("GET", "/api/v1/solicitacoes/minhas", "Problemas", "Listar meus problemas", "Lista problemas do usuário."),
                new SwaggerEndpoint("GET", "/api/v1/solicitacoes/selecionar/{problemaId}", "Problemas", "Consultar problema", "Consulta um problema."),
                new SwaggerEndpoint("POST", "/api/v1/solicitacoes", "Problemas", "Cadastrar problema", "Cria um problema."),
                new SwaggerEndpoint("PATCH", "/api/v1/solicitacoes/status", "Problemas", "Atualizar status", "Atualiza o status de um problema."));
    }

    @Operation(summary = "Listar problemas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Problemas encontrados", content = @Content(schema = @Schema(implementation = ProblemaOutputDTO.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public abstract ResponseEntity<List<ProblemaOutputDTO>> listar(@Parameter(description = "Campos separados por vírgula", in = ParameterIn.QUERY) String campos, FiltrosProblemaQueryParam filtros, Authentication authentication);

    @Operation(summary = "Listar meus problemas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Problemas encontrados", content = @Content(schema = @Schema(implementation = ProblemaOutputDTO.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public abstract ResponseEntity<List<ProblemaOutputDTO>> listarMinhas(@Parameter(description = "Campos separados por vírgula", in = ParameterIn.QUERY) String campos, FiltrosProblemaQueryParam filtros, Authentication authentication);

    @Operation(summary = "Consultar problema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Problema encontrado", content = @Content(schema = @Schema(implementation = ProblemaOutputDTO.class))),
            @ApiResponse(responseCode = "404", description = "Problema não encontrado")
    })
    public abstract ResponseEntity<ProblemaOutputDTO> listarDetalhes(@Parameter(required = true, description = "Identificador do problema") Long problemaId, @Parameter(description = "Campos separados por vírgula", in = ParameterIn.QUERY) String campos, Authentication authentication);

    @Operation(summary = "Cadastrar problema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Problema criado", content = @Content(schema = @Schema(implementation = ProblemaOutputDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public abstract ResponseEntity<ProblemaOutputDTO> cadastrar(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do problema", required = true) ProblemaCriarInputDTO input, Authentication authentication);

    @Operation(summary = "Atualizar status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status atualizado", content = @Content(schema = @Schema(implementation = ProblemaOutputDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public abstract ResponseEntity<ProblemaOutputDTO> atualizaStatus(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do status", required = true) ProblemaAtualizarStatusDTO input, Authentication authentication);
}


