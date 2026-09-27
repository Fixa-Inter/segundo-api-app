package com.example.segundoapiappfixa.infrastructure.swagger;

import com.example.segundoapiappfixa.adapters.controller.contract.ModeloEquipamentoControllerContract;
import com.example.segundoapiappfixa.adapters.dto.input.ModeloEquipamento.ModeloEquipamentoAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.input.ModeloEquipamento.ModeloEquipamentoCadastrarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.ModeloEquipamento.ModeloEquipamentoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosModeloEquipamentoQueryParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

@Tag(
        name = "Modelos de equipamento",
        description = "Gerenciamento dos modelos de equipamento."
)
@SecurityRequirement(name = "bearerAuth")
public abstract class ModeloEquipamentoSwagger implements ModeloEquipamentoControllerContract {

    public static List<SwaggerEndpoint> endpoints() {
        return List.of(
                new SwaggerEndpoint("GET", "/api/v1/equipamentos/modelos", "Modelos de equipamento", "Listar modelos", "Lista modelos com filtros e campos selecionáveis."),
                new SwaggerEndpoint("GET", "/api/v1/equipamentos/modelos/{modeloEquipamentoId}", "Modelos de equipamento", "Consultar modelo", "Consulta um modelo."),
                new SwaggerEndpoint("POST", "/api/v1/equipamentos/modelos", "Modelos de equipamento", "Cadastrar modelo", "Cria um modelo."),
                new SwaggerEndpoint("PATCH", "/api/v1/equipamentos/modelos", "Modelos de equipamento", "Atualizar modelo", "Atualiza um modelo."),
                new SwaggerEndpoint("DELETE", "/api/v1/equipamentos/modelos/{modeloEquipamentoId}", "Modelos de equipamento", "Remover modelo", "Remove um modelo.")
        );
    }

    @Override
    @Operation(summary = "Listar modelos", description = "Lista modelos com filtros e campos selecionáveis.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modelos encontrados", content = @Content(schema = @Schema(implementation = ModeloEquipamentoOutputDTO.class))),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    public abstract ResponseEntity<List<ModeloEquipamentoOutputDTO>> listar(
            @Parameter(description = "Campos de saída separados por vírgula", in = ParameterIn.QUERY, example = "id,nome,estaAtivo")
            String campos,
            FiltrosModeloEquipamentoQueryParam filtros,
            Authentication authentication
    );

    @Override
    @Operation(summary = "Consultar modelo", description = "Consulta um modelo pelo identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modelo encontrado", content = @Content(schema = @Schema(implementation = ModeloEquipamentoOutputDTO.class))),
            @ApiResponse(responseCode = "404", description = "Modelo não encontrado")
    })
    public abstract ResponseEntity<ModeloEquipamentoOutputDTO> listarDetalhes(
            @Parameter(description = "Identificador do modelo", required = true, example = "1")
            Long modeloEquipamentoId,
            @Parameter(description = "Campos de saída separados por vírgula", in = ParameterIn.QUERY, example = "id,nome")
            String campos,
            Authentication authentication
    );

    @Override
    @Operation(summary = "Cadastrar modelo", description = "Cria um novo modelo de equipamento.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Modelo criado", content = @Content(schema = @Schema(implementation = ModeloEquipamentoOutputDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    public abstract ResponseEntity<ModeloEquipamentoOutputDTO> cadastrar(
            @RequestBody(description = "Dados do modelo", required = true, content = @Content(schema = @Schema(implementation = ModeloEquipamentoCadastrarInputDTO.class)))
            ModeloEquipamentoCadastrarInputDTO dto,
            Authentication authentication
    );

    @Override
    @Operation(summary = "Atualizar modelo", description = "Atualiza os dados de um modelo de equipamento.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modelo atualizado", content = @Content(schema = @Schema(implementation = ModeloEquipamentoOutputDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    public abstract ResponseEntity<ModeloEquipamentoOutputDTO> atualizar(
            @RequestBody(description = "Dados do modelo", required = true, content = @Content(schema = @Schema(implementation = ModeloEquipamentoAtualizarInputDTO.class)))
            ModeloEquipamentoAtualizarInputDTO dto,
            Authentication authentication
    );

    @Override
    @Operation(summary = "Remover modelo", description = "Remove um modelo de equipamento.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modelo removido", content = @Content(schema = @Schema(implementation = ModeloEquipamentoOutputDTO.class))),
            @ApiResponse(responseCode = "404", description = "Modelo não encontrado")
    })
    public abstract ResponseEntity<ModeloEquipamentoOutputDTO> deletar(
            @Parameter(description = "Identificador do modelo", required = true, example = "1")
            Long modeloEquipamentoId,
            Authentication authentication
    );
}
