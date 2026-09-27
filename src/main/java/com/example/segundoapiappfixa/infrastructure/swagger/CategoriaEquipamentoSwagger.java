package com.example.segundoapiappfixa.infrastructure.swagger;

import com.example.segundoapiappfixa.adapters.controller.contract.CategoriaEquipamentoControllerContract;
import com.example.segundoapiappfixa.adapters.dto.input.CategoriaEquipamento.CategoriaEquipamentoAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.input.CategoriaEquipamento.CategoriaEquipamentoCadastrarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.CategoriaEquipamento.CategoriaEquipamentoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosCategoriaEquipamentoQueryParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
        name = "Categorias de Equipamento",
        description = "Gerenciamento das categorias usadas para classificar equipamentos."
)
@SecurityRequirement(name = "bearerAuth")
public abstract class CategoriaEquipamentoSwagger implements CategoriaEquipamentoControllerContract {

    public static List<SwaggerEndpoint> endpoints() {
        return List.of(
                new SwaggerEndpoint("GET", "/api/v1/equipamentos/categorias", "Categorias de equipamento", "Listar categorias", "Lista as categorias de equipamento acessíveis ao usuário autenticado, com filtros e seleção dinâmica de campos."),
                new SwaggerEndpoint("GET", "/api/v1/equipamentos/categorias/{categoriaEquipamentoId}", "Categorias de equipamento", "Consultar categoria", "Consulta uma categoria de equipamento pelo identificador."),
                new SwaggerEndpoint("POST", "/api/v1/equipamentos/categorias", "Categorias de equipamento", "Cadastrar categoria", "Cria uma nova categoria de equipamento."),
                new SwaggerEndpoint("PATCH", "/api/v1/equipamentos/categorias", "Categorias de equipamento", "Atualizar categoria", "Atualiza os dados de uma categoria de equipamento."),
                new SwaggerEndpoint("DELETE", "/api/v1/equipamentos/categorias/{categoriaId}", "Categorias de equipamento", "Remover categoria", "Remove uma categoria de equipamento.")
        );
    }

    @Override
    @Operation(
            summary = "Listar categorias",
            description = "Lista as categorias de equipamento acessíveis ao usuário autenticado, com filtros e seleção dinâmica de campos."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Categorias encontradas",
                    content = @Content(
                            schema = @Schema(implementation = CategoriaEquipamentoOutputDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "404", description = "Nenhuma categoria encontrada")
    })
    public abstract ResponseEntity<List<CategoriaEquipamentoOutputDTO>> listar(
            FiltrosCategoriaEquipamentoQueryParam filtros,

            @Parameter(
                    description = "Campos de saída separados por vírgula",
                    in = ParameterIn.QUERY,
                    example = "id,nome,estaAtivo"
            )
            String campos,

            Authentication authentication
    );

    @Override
    @Operation(
            summary = "Consultar categoria",
            description = "Consulta uma categoria de equipamento pelo identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Categoria encontrada",
                    content = @Content(
                            schema = @Schema(implementation = CategoriaEquipamentoOutputDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public abstract ResponseEntity<CategoriaEquipamentoOutputDTO> listarDetalhes(
            @Parameter(
                    description = "Identificador da categoria",
                    required = true,
                    example = "1"
            )
            Long categoriaEquipamentoId,

            String campos,
            Authentication authentication
    );

    @Override
    @Operation(
            summary = "Cadastrar categoria",
            description = "Cria uma nova categoria de equipamento."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Categoria criada",
                    content = @Content(
                            schema = @Schema(implementation = CategoriaEquipamentoOutputDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    public abstract ResponseEntity<CategoriaEquipamentoOutputDTO> cadastrar(
            CategoriaEquipamentoCadastrarInputDTO dto,
            Authentication authentication
    );

    @Override
    @Operation(
            summary = "Atualizar categoria",
            description = "Atualiza os dados de uma categoria de equipamento."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Categoria atualizada",
                    content = @Content(
                            schema = @Schema(implementation = CategoriaEquipamentoOutputDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    public abstract ResponseEntity<CategoriaEquipamentoOutputDTO> atualizar(
            CategoriaEquipamentoAtualizarInputDTO dto,
            Authentication authentication
    );

    @Override
    @Operation(
            summary = "Remover categoria",
            description = "Remove uma categoria de equipamento."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Categoria removida",
                    content = @Content(
                            schema = @Schema(implementation = CategoriaEquipamentoOutputDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public abstract ResponseEntity<CategoriaEquipamentoOutputDTO> deletar(
            @Parameter(
                    description = "Identificador da categoria",
                    required = true,
                    example = "1"
            )
            Long categoriaId,

            Authentication authentication
    );
}
