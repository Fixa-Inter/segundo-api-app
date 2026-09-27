package com.example.segundoapiappfixa.infrastructure.swagger;

import com.example.segundoapiappfixa.adapters.controller.contract.TarefaControllerContract;
import com.example.segundoapiappfixa.adapters.dto.input.Tarefa.*;
import com.example.segundoapiappfixa.adapters.dto.output.Tarefa.TarefaOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosTarefaQueryParam;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

@Tag(name = "Tarefas", description = "Gerenciamento das tarefas de uma ordem de serviço.")
@SecurityRequirement(name = "bearerAuth")
public abstract class TarefaSwagger implements TarefaControllerContract {
    public static List<SwaggerEndpoint> endpoints() {
        return List.of(new SwaggerEndpoint("GET", "/api/v1/tarefas/{ordemServicoId}", "Tarefas", "Listar tarefas", "Lista tarefas com filtros."),
                new SwaggerEndpoint("GET", "/api/v1/tarefas/selecionar/{tarefaId}", "Tarefas", "Consultar tarefa", "Consulta uma tarefa."),
                new SwaggerEndpoint("POST", "/api/v1/tarefas", "Tarefas", "Cadastrar tarefas", "Cria tarefas em lote."),
                new SwaggerEndpoint("PATCH", "/api/v1/tarefas", "Tarefas", "Atualizar tarefa", "Atualiza uma tarefa."),
                new SwaggerEndpoint("DELETE", "/api/v1/tarefas/{tarefaId}", "Tarefas", "Remover tarefa", "Remove uma tarefa."));
    }

    @Operation(summary = "Listar tarefas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefas encontradas", content = @Content(schema = @Schema(implementation = TarefaOutputDTO.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public abstract ResponseEntity<List<TarefaOutputDTO>> listar(@Parameter(required = true, description = "Identificador da ordem") Long ordemServicoId, @Parameter(description = "Campos separados por vírgula", in = ParameterIn.QUERY) String campos, FiltrosTarefaQueryParam filtros, Authentication authentication);

    @Operation(summary = "Consultar tarefa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada", content = @Content(schema = @Schema(implementation = TarefaOutputDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    public abstract ResponseEntity<TarefaOutputDTO> listarDetalhes(@Parameter(required = true, description = "Identificador da tarefa") Long tarefaId, @Parameter(description = "Campos separados por vírgula", in = ParameterIn.QUERY) String campos, Authentication authentication);

    @Operation(summary = "Cadastrar tarefas")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tarefas criadas", content = @Content(schema = @Schema(implementation = TarefaOutputDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public abstract ResponseEntity<List<TarefaOutputDTO>> cadastrar(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Lista de tarefas", required = true) List<TarefaCriarInputDTO> input, Authentication authentication);

    @Operation(summary = "Atualizar tarefa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada", content = @Content(schema = @Schema(implementation = TarefaOutputDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public abstract ResponseEntity<TarefaOutputDTO> atualizar(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados da tarefa", required = true) TarefaAtualizarInputDTO input, Authentication authentication);

    @Operation(summary = "Remover tarefa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefa removida", content = @Content(schema = @Schema(implementation = TarefaOutputDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    public abstract ResponseEntity<TarefaOutputDTO> deletar(@Parameter(required = true, description = "Identificador da tarefa") Long tarefaId, Authentication authentication);
}


