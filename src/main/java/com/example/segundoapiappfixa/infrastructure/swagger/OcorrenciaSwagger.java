package com.example.segundoapiappfixa.infrastructure.swagger;

import com.example.segundoapiappfixa.adapters.controller.contract.OcorrenciaControllerContract;
import com.example.segundoapiappfixa.adapters.dto.input.Ocorrencia.*;
import com.example.segundoapiappfixa.adapters.dto.output.Ocorrencia.OcorrenciaOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosOcorrenciaQueryParam;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

@Tag(name = "Ocorrências", description = "Registro e acompanhamento de ocorrências.")
@SecurityRequirement(name = "bearerAuth")
public abstract class OcorrenciaSwagger implements OcorrenciaControllerContract {
    public static List<SwaggerEndpoint> endpoints() {
        return List.of(new SwaggerEndpoint("GET", "/api/v1/ocorrencias", "Ocorrências", "Listar ocorrências", "Lista ocorrências com filtros e período de criação."),
                new SwaggerEndpoint("GET", "/api/v1/ocorrencias/minhas", "Ocorrências", "Listar minhas ocorrências", "Lista ocorrências do usuário."),
                new SwaggerEndpoint("GET", "/api/v1/ocorrencias/{ocorrenciaId}", "Ocorrências", "Consultar ocorrência", "Consulta uma ocorrência."),
                new SwaggerEndpoint("POST", "/api/v1/ocorrencias", "Ocorrências", "Cadastrar ocorrência", "Cria uma ocorrência."),
                new SwaggerEndpoint("PATCH", "/api/v1/ocorrencias", "Ocorrências", "Atualizar ocorrência", "Atualiza uma ocorrência."),
                new SwaggerEndpoint("DELETE", "/api/v1/ocorrencias/{ocorrenciaId}", "Ocorrências", "Remover ocorrência", "Remove uma ocorrência."));
    }

    @Operation(summary = "Listar ocorrências")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ocorrências encontradas", content = @Content(schema = @Schema(implementation = OcorrenciaOutputDTO.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public abstract ResponseEntity<List<OcorrenciaOutputDTO>> listar(@Parameter(description = "Campos separados por vírgula", in = ParameterIn.QUERY) String campos, FiltrosOcorrenciaQueryParam filtros, Authentication authentication);

    @Operation(summary = "Listar minhas ocorrências")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ocorrências encontradas", content = @Content(schema = @Schema(implementation = OcorrenciaOutputDTO.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public abstract ResponseEntity<List<OcorrenciaOutputDTO>> listarMinhas(@Parameter(description = "Campos separados por vírgula", in = ParameterIn.QUERY) String campos, FiltrosOcorrenciaQueryParam filtros, Authentication authentication);

    @Operation(summary = "Consultar ocorrência")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ocorrência encontrada", content = @Content(schema = @Schema(implementation = OcorrenciaOutputDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ocorrência não encontrada")
    })
    public abstract ResponseEntity<OcorrenciaOutputDTO> listarDetalhes(@Parameter(required = true, description = "Identificador da ocorrência") Long ocorrenciaId, @Parameter(description = "Campos separados por vírgula", in = ParameterIn.QUERY) String campos, Authentication authentication);

    @Operation(summary = "Cadastrar ocorrência")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ocorrência criada", content = @Content(schema = @Schema(implementation = OcorrenciaOutputDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public abstract ResponseEntity<OcorrenciaOutputDTO> cadastrar(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados da ocorrência", required = true) OcorrenciaCadastrarInputDTO input, Authentication authentication);

    @Operation(summary = "Atualizar ocorrência")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ocorrência atualizada", content = @Content(schema = @Schema(implementation = OcorrenciaOutputDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public abstract ResponseEntity<OcorrenciaOutputDTO> atualizar(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados da ocorrência", required = true) OcorrenciaAtualizarInputDTO input, Authentication authentication);

    @Operation(summary = "Remover ocorrência")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ocorrência removida", content = @Content(schema = @Schema(implementation = OcorrenciaOutputDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ocorrência não encontrada")
    })
    public abstract ResponseEntity<OcorrenciaOutputDTO> deletar(@Parameter(required = true, description = "Identificador da ocorrência") Long ocorrenciaId, Authentication authentication);
}

