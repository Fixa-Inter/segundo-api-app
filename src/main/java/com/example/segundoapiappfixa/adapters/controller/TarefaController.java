package com.example.segundoapiappfixa.adapters.controller;

import com.example.segundoapiappfixa.adapters.dto.input.Tarefa.TarefaAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.input.Tarefa.TarefaCriarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Tarefa.TarefaOutputDTO;
import com.example.segundoapiappfixa.adapters.mapper.TarefaMapper;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.DynamicFieldFilter;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.TarefaDynamicMapper;
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
    private final TarefaDynamicMapper tarefaDynamicMapper;

    @GetMapping("/{ordemServicoId}")
    public ResponseEntity<List<TarefaOutputDTO>> listar(
            @PathVariable
            Long ordemServicoId,

            @RequestParam(required = false)
            String campos,

            Authentication authentication
    ) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        boolean isGestor = authentication
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_GESTOR"));

        return ResponseEntity.ok(
                mask(toOutputDTO(listarTarefas.listarTarefasPelaOrdemServico(
                        ordemServicoId, isGestor, authenticatedUser.id()
                )), campos
        ));
    }

    @PostMapping()
    public ResponseEntity<List<TarefaOutputDTO>> cadastrar(
            @RequestBody
            @Valid
            @NotEmpty(message = "{validation.tarefa.lista.required}")
            List<@Valid TarefaCriarInputDTO> tarefaCriarInputDTOS,

            @RequestParam(required = false) String campos,

            Authentication authentication
    ) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toOutputDTO(criarTarefa.criarTarefas(
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

            @RequestParam(required = false) String campos,

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

            @RequestParam(required = false) String campos,

            Authentication authentication
    ) {

        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        boolean isGestor = authentication
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_GESTOR"));

        return ResponseEntity.ok(
                tarefaMapper.toOutputDTO(deletarTarefa.deletarTarefa(tarefaId, isGestor, authenticatedUser.id()))
        );

    }

    private List<TarefaOutputDTO> toOutputDTO(List<Tarefa> tarefas) {
        return tarefas.stream().map(tarefaMapper::toOutputDTO).toList();
    }

    private List<TarefaOutputDTO> mask(List<TarefaOutputDTO> dtos, String campos) {
        if (dtos.isEmpty()) return List.of();

        List<String> available = DynamicFieldFilter.availableFields(dtos.getFirst());
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dtos
                .stream()
                .map(dto -> tarefaDynamicMapper.mask(dto, selected))
                .toList();
    }
}
