package com.example.segundoapiappfixa.infrastructure.swagger;

import com.example.segundoapiappfixa.adapters.controller.contract.MarcaEquipamentoControllerContract;
import com.example.segundoapiappfixa.adapters.dto.input.MarcaEquipamento.MarcaEquipamentoAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.input.MarcaEquipamento.MarcaEquipamentoCadastrarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.MarcaEquipamento.MarcaEquipamentoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosMarcaEquipamentoQueryParam;
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

import java.util.List;

@Tag(
        name = "Marcas de equipamento",
        description = "Gerenciamento das marcas de equipamento."
)
@SecurityRequirement(name = "bearerAuth")
public abstract class MarcaEquipamentoSwagger implements MarcaEquipamentoControllerContract {

    public static List<SwaggerEndpoint> endpoints() {
        return List.of(
                new SwaggerEndpoint("GET", "/api/v1/equipamentos/marcas", "Marcas de equipamento", "Listar marcas", "Lista marcas com filtros e campos selecionáveis."),
                new SwaggerEndpoint("GET", "/api/v1/equipamentos/marcas/{marcaEquipamentoId}", "Marcas de equipamento", "Consultar marca", "Consulta uma marca."),
                new SwaggerEndpoint("POST", "/api/v1/equipamentos/marcas", "Marcas de equipamento", "Cadastrar marca", "Cria uma marca."),
                new SwaggerEndpoint("PATCH", "/api/v1/equipamentos/marcas", "Marcas de equipamento", "Atualizar marca", "Atualiza uma marca."),
                new SwaggerEndpoint("DELETE", "/api/v1/equipamentos/marcas/{marcaEquipamentoId}", "Marcas de equipamento", "Remover marca", "Remove uma marca.")
        );
    }

    @Override
    @Operation(summary = "Listar marcas", description = "Lista marcas com filtros e campos selecionáveis.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Marcas encontradas", content = @Content(schema = @Schema(implementation = MarcaEquipamentoOutputDTO.class))),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    public abstract ResponseEntity<List<MarcaEquipamentoOutputDTO>> listar(
            @Parameter(description = "Campos de saída separados por vírgula", in = ParameterIn.QUERY, example = "id,nome,estaAtivo")
            String campos,
            FiltrosMarcaEquipamentoQueryParam filtros
    );

    @Override
    @Operation(summary = "Consultar marca", description = "Consulta uma marca pelo identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Marca encontrada", content = @Content(schema = @Schema(implementation = MarcaEquipamentoOutputDTO.class))),
            @ApiResponse(responseCode = "404", description = "Marca não encontrada")
    })
    public abstract ResponseEntity<MarcaEquipamentoOutputDTO> listarDetalhes(
            @Parameter(description = "Identificador da marca", required = true, example = "1")
            Long marcaEquipamentoId,
            @Parameter(description = "Campos de saída separados por vírgula", in = ParameterIn.QUERY, example = "id,nome")
            String campos
    );

    @Override
    @Operation(summary = "Cadastrar marca", description = "Cria uma nova marca de equipamento.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Marca criada", content = @Content(schema = @Schema(implementation = MarcaEquipamentoOutputDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    public abstract ResponseEntity<MarcaEquipamentoOutputDTO> cadastrar(
            @RequestBody(description = "Dados da marca", required = true, content = @Content(schema = @Schema(implementation = MarcaEquipamentoCadastrarInputDTO.class)))
            MarcaEquipamentoCadastrarInputDTO dto
    );

    @Override
    @Operation(summary = "Atualizar marca", description = "Atualiza os dados de uma marca de equipamento.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Marca atualizada", content = @Content(schema = @Schema(implementation = MarcaEquipamentoOutputDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    public abstract ResponseEntity<MarcaEquipamentoOutputDTO> atualizar(
            @RequestBody(description = "Dados da marca", required = true, content = @Content(schema = @Schema(implementation = MarcaEquipamentoAtualizarInputDTO.class)))
            MarcaEquipamentoAtualizarInputDTO dto
    );

    @Override
    @Operation(summary = "Remover marca", description = "Remove uma marca de equipamento.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Marca removida", content = @Content(schema = @Schema(implementation = MarcaEquipamentoOutputDTO.class))),
            @ApiResponse(responseCode = "404", description = "Marca não encontrada")
    })
    public abstract ResponseEntity<MarcaEquipamentoOutputDTO> deletar(
            @Parameter(description = "Identificador da marca", required = true, example = "1")
            Long marcaEquipamentoId
    );
}
