package com.example.segundoapiappfixa.infrastructure.swagger;

import com.example.segundoapiappfixa.adapters.controller.contract.EquipamentoControllerContract;
import com.example.segundoapiappfixa.adapters.dto.input.Equipamento.EquipamentoAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.input.Equipamento.EquipamentoCadastrarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Equipamento.EquipamentoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosEquipamentoQueryParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

@Tag(
        name = "Equipamentos",
        description = "Gerenciamento dos equipamentos cadastrados e vinculados aos seus modelos."
)
@SecurityRequirement(name = "bearerAuth")
public abstract class EquipamentoSwagger implements EquipamentoControllerContract {

    public static List<SwaggerEndpoint> endpoints() {
        return List.of(
                new SwaggerEndpoint("GET", "/api/v1/equipamentos/modelo/{modeloEquipamentoId}", "Equipamentos", "Listar equipamentos", "Lista os equipamentos de um modelo, com filtros e seleção dinâmica de campos."),
                new SwaggerEndpoint("GET", "/api/v1/equipamentos/{equipamentoId}", "Equipamentos", "Consultar equipamento", "Consulta um equipamento pelo identificador."),
                new SwaggerEndpoint("POST", "/api/v1/equipamentos", "Equipamentos", "Cadastrar equipamento", "Cria um novo equipamento."),
                new SwaggerEndpoint("PATCH", "/api/v1/equipamentos", "Equipamentos", "Atualizar equipamento", "Atualiza os dados de um equipamento."),
                new SwaggerEndpoint("DELETE", "/api/v1/equipamentos/{equipamentoId}", "Equipamentos", "Remover equipamento", "Remove um equipamento pelo identificador.")
        );
    }

    @Override
    @Operation(summary = "Listar equipamentos", description = "Lista os equipamentos de um modelo, com filtros e seleção dinâmica de campos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipamentos encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EquipamentoOutputDTO.class)))),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "404", description = "Nenhum equipamento encontrado")
    })
    public abstract ResponseEntity<List<EquipamentoOutputDTO>> listar(
            @Parameter(description = "Identificador do modelo do equipamento", required = true, example = "1")
            Long modeloEquipamentoId,
            FiltrosEquipamentoQueryParam filtros,
            @Parameter(description = "Campos de saída separados por vírgula", in = ParameterIn.QUERY, example = "id,codigo,modeloEquipamento,estaAtivo")
            String campos,
            Authentication authentication
    );

    @Override
    @Operation(summary = "Consultar equipamento", description = "Consulta um equipamento pelo identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipamento encontrado", content = @Content(schema = @Schema(implementation = EquipamentoOutputDTO.class))),
            @ApiResponse(responseCode = "404", description = "Equipamento não encontrado")
    })
    public abstract ResponseEntity<EquipamentoOutputDTO> listarDetalhes(
            @Parameter(description = "Identificador do equipamento", required = true, example = "1")
            Long equipamentoId,
            @Parameter(description = "Campos de saída separados por vírgula", in = ParameterIn.QUERY, example = "id,codigo,estaAtivo")
            String campos,
            Authentication authentication
    );

    @Override
    @Operation(summary = "Cadastrar equipamento", description = "Cria um novo equipamento.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Equipamento criado", content = @Content(schema = @Schema(implementation = EquipamentoOutputDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    public abstract ResponseEntity<EquipamentoOutputDTO> cadastrar(EquipamentoCadastrarInputDTO dto, Authentication authentication);

    @Override
    @Operation(summary = "Atualizar equipamento", description = "Atualiza os dados de um equipamento.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipamento atualizado", content = @Content(schema = @Schema(implementation = EquipamentoOutputDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    public abstract ResponseEntity<EquipamentoOutputDTO> atualizar(EquipamentoAtualizarInputDTO dto, Authentication authentication);

    @Override
    @Operation(summary = "Remover equipamento", description = "Remove um equipamento pelo identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipamento removido", content = @Content(schema = @Schema(implementation = EquipamentoOutputDTO.class))),
            @ApiResponse(responseCode = "404", description = "Equipamento não encontrado")
    })
    public abstract ResponseEntity<EquipamentoOutputDTO> deletar(
            @Parameter(description = "Identificador do equipamento", required = true, example = "1")
            Long equipamentoId,
            Authentication authentication
    );
}
