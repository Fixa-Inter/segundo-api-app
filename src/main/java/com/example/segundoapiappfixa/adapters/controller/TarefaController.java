package com.example.segundoapiappfixa.adapters.controller;

import com.example.segundoapiappfixa.adapters.dto.input.Tarefa.TarefaAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.input.Tarefa.TarefaCriarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.OrdemServico.OrdemServicoDetalhesOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Tarefa.TarefaOutputDTO;
import com.example.segundoapiappfixa.adapters.mapper.TarefaMapper;
import com.example.segundoapiappfixa.application.usecase.Tarefa.AtualizarTarefa;
import com.example.segundoapiappfixa.application.usecase.Tarefa.CriarTarefa;
import com.example.segundoapiappfixa.application.usecase.Tarefa.DeletarTarefa;
import com.example.segundoapiappfixa.application.usecase.Tarefa.ListarTarefas;
import com.example.segundoapiappfixa.auth.dto.AuthenticatedUser;
import com.example.segundoapiappfixa.domain.model.Tarefa;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final ListarTarefas listarTarefas;
    private final CriarTarefa criarTarefa;
    private final AtualizarTarefa atualizarTarefa;
    private final DeletarTarefa deletarTarefa;
    private final TarefaMapper tarefaMapper;

    @GetMapping("/{ordemServicoId}")
    public ResponseEntity<List<TarefaOutputDTO>> listar(
            @PathVariable
            Long ordemServicoId,

            Authentication authentication
    ) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        boolean isGestor = authentication
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_GESTOR"));

        return ResponseEntity.ok(
                toOutputDTO(listarTarefas.listarTarefasPelaOrdemServico(
                        ordemServicoId, isGestor, authenticatedUser.id()
                ))
        );
    }

    @PostMapping()
    public ResponseEntity<List<TarefaOutputDTO>> cadastrar(
            @RequestBody
            @Valid
            @NotEmpty(message = "{validation.tarefa.lista.required}")
            List<@Valid TarefaCriarInputDTO> tarefaCriarInputDTOS,

            Authentication authentication
    ) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        toOutputDTO(criarTarefa.criarTarefas(
                                tarefaCriarInputDTOS,
                                authenticatedUser.id()
                        ))
                );
    }

    @PatchMapping
    public ResponseEntity<TarefaOutputDTO> atualizar(
            @RequestBody
            @Valid
            TarefaAtualizarInputDTO atualizarInputDTO,

            Authentication authentication
    ) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        return ResponseEntity.ok(
                tarefaMapper.toOutputDTO(atualizarTarefa.atualizar(
                        atualizarInputDTO,
                        authenticatedUser.id()
                ))
        );
    }

    @DeleteMapping("/{tarefaId}")
    public ResponseEntity<TarefaOutputDTO> deletar(
            @PathVariable
            Long tarefaId,

            Authentication authentication
    ) {

        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        boolean isGestor = authentication
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_GESTOR"));

        return ResponseEntity.ok(
                tarefaMapper.toOutputDTO(
                        deletarTarefa.deletarTarefa(tarefaId, isGestor, authenticatedUser.id())
                )
        );

    }

    private List<TarefaOutputDTO> toOutputDTO(List<Tarefa> tarefas) {
        return tarefas.stream().map(tarefaMapper::toOutputDTO).toList();
    }
}
