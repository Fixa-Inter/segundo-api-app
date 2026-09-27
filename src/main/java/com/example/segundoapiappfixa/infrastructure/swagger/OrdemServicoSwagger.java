package com.example.segundoapiappfixa.infrastructure.swagger;

import com.example.segundoapiappfixa.adapters.controller.contract.OrdemServicoControllerContract;
import com.example.segundoapiappfixa.adapters.dto.input.OrdemServico.*;
import com.example.segundoapiappfixa.adapters.dto.output.OrdemServico.OrdemServicoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosOrdemServicoQueryParam;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

@Tag(name = "Ordens de serviço", description = "Abertura, acompanhamento e manutenção de ordens de serviço.")
@SecurityRequirement(name = "bearerAuth")
public abstract class OrdemServicoSwagger implements OrdemServicoControllerContract {
    public static List<SwaggerEndpoint> endpoints() {
        return List.of(new SwaggerEndpoint("GET", "/api/v1/os", "Ordens de serviço", "Listar ordens", "Lista ordens com filtros e data prevista."),
                new SwaggerEndpoint("GET", "/api/v1/os/minhas", "Ordens de serviço", "Listar minhas ordens", "Lista ordens do usuário."),
                new SwaggerEndpoint("GET", "/api/v1/os/{ordemServicoId}", "Ordens de serviço", "Consultar ordem", "Consulta uma ordem."),
                new SwaggerEndpoint("POST", "/api/v1/os", "Ordens de serviço", "Cadastrar ordem", "Cria uma ordem."),
                new SwaggerEndpoint("PATCH", "/api/v1/os", "Ordens de serviço", "Atualizar ordem", "Atualiza uma ordem."),
                new SwaggerEndpoint("DELETE", "/api/v1/os/{ordemServicoId}", "Ordens de serviço", "Remover ordem", "Remove uma ordem."));
    }

    @Operation(summary = "Listar ordens de serviço")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ordens encontradas", content = @Content(schema = @Schema(implementation = OrdemServicoOutputDTO.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public abstract ResponseEntity<List<OrdemServicoOutputDTO>> listar(@Parameter(description = "Campos separados por vírgula", in = ParameterIn.QUERY) String campos, FiltrosOrdemServicoQueryParam filtros, Authentication authentication);

    @Operation(summary = "Listar minhas ordens")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ordens encontradas", content = @Content(schema = @Schema(implementation = OrdemServicoOutputDTO.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public abstract ResponseEntity<List<OrdemServicoOutputDTO>> listarMinhas(@Parameter(description = "Campos separados por vírgula", in = ParameterIn.QUERY) String campos, FiltrosOrdemServicoQueryParam filtros, Authentication authentication);

    @Operation(summary = "Consultar ordem")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ordem encontrada", content = @Content(schema = @Schema(implementation = OrdemServicoOutputDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ordem não encontrada")
    })
    public abstract ResponseEntity<OrdemServicoOutputDTO> listarDetalhes(@Parameter(required = true, description = "Identificador da ordem") Long ordemServicoId, @Parameter(description = "Campos separados por vírgula", in = ParameterIn.QUERY) String campos, Authentication authentication);

    @Operation(summary = "Cadastrar ordem")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ordem criada", content = @Content(schema = @Schema(implementation = OrdemServicoOutputDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public abstract ResponseEntity<OrdemServicoOutputDTO> cadastrar(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados da ordem", required = true) OrdemServicoCadastrarInputDTO input, Authentication authentication);

    @Operation(summary = "Atualizar ordem")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ordem atualizada", content = @Content(schema = @Schema(implementation = OrdemServicoOutputDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public abstract ResponseEntity<OrdemServicoOutputDTO> atualizar(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados da ordem", required = true) OrdemServicoAtualizarInputDTO input, Authentication authentication);

    @Operation(summary = "Remover ordem")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ordem removida", content = @Content(schema = @Schema(implementation = OrdemServicoOutputDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ordem não encontrada")
    })
    public abstract ResponseEntity<OrdemServicoOutputDTO> deletarOrdemServico(@Parameter(required = true, description = "Identificador da ordem") Long ordemServicoId, Authentication authentication);
}


