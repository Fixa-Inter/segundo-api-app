package com.example.segundoapiappfixa.adapters.controller;

import com.example.segundoapiappfixa.adapters.dto.input.Tarefa.TarefaAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.input.Tarefa.TarefaCriarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Tarefa.TarefaOutputDTO;
import com.example.segundoapiappfixa.adapters.mapper.TarefaMapper;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.DynamicFieldFilter;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.TarefaDynamicMapper;
import com.example.segundoapiappfixa.adapters.utils.ControllerUtils;
import com.example.segundoapiappfixa.application.usecase.Tarefa.AtualizarTarefa;
import com.example.segundoapiappfixa.application.usecase.Tarefa.CriarTarefa;
import com.example.segundoapiappfixa.application.usecase.Tarefa.DeletarTarefa;
import com.example.segundoapiappfixa.application.usecase.Tarefa.ListarTarefas;
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

    // UseCases
    private final ListarTarefas listarTarefas;
    private final CriarTarefa criarTarefa;
    private final AtualizarTarefa atualizarTarefa;
    private final DeletarTarefa deletarTarefa;


    // Mappers
    private final TarefaMapper mapper;
    private final TarefaDynamicMapper dynamicMapper;

    // GET
    @GetMapping("/{ordemServicoId}")
    public ResponseEntity<List<TarefaOutputDTO>> listar(
            @PathVariable
            Long ordemServicoId,

            @RequestParam(required = false)
            String campos,

            Authentication authentication
    ) {
        return ResponseEntity.ok(
                mask(toOutputDTO(listarTarefas.listar(
                        ordemServicoId,
                        ControllerUtils.isGestor(authentication),
                        ControllerUtils.usuarioId(authentication)
                )), campos
        ));
    }

    @GetMapping("/selecionar/{tarefaId}")
    public ResponseEntity<TarefaOutputDTO> listarDetalhes(
            @PathVariable
            Long tarefaId,

            @RequestParam(required = false)
            String campos,

            Authentication authentication
    ) {
        TarefaOutputDTO dto = mapper.toOutputDTO(listarTarefas.listarDetalhes(
                tarefaId,
                ControllerUtils.isGestor(authentication),
                ControllerUtils.usuarioId(authentication)
        ));

        return ResponseEntity.ok(mask(dto, campos));
    }

    // POST
    @PostMapping()
    public ResponseEntity<List<TarefaOutputDTO>> cadastrar(
            @RequestBody
            @Valid
            @NotEmpty(message = "{validation.tarefa.lista.required}")
            List<@Valid TarefaCriarInputDTO> tarefaCriarInputDTOS,

            @RequestParam(required = false) String campos,

            Authentication authentication
    ) {
       return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toOutputDTO(criarTarefa.criarTarefas(
                                tarefaCriarInputDTOS,
                                ControllerUtils.usuarioId(authentication)
                        ))
                );
    }

    // PATCH
    @PatchMapping
    public ResponseEntity<TarefaOutputDTO> atualizar(
            @RequestBody
            @Valid
            TarefaAtualizarInputDTO atualizarInputDTO,

            @RequestParam(required = false) String campos,

            Authentication authentication
    ) {
        return ResponseEntity.ok(
                        mapper.toOutputDTO(atualizarTarefa.atualizar(
                        atualizarInputDTO,
                        ControllerUtils.usuarioId(authentication)
                ))
        );
    }

    // DELETE
    @DeleteMapping("/{tarefaId}")
    public ResponseEntity<TarefaOutputDTO> deletar(
            @PathVariable
            Long tarefaId,

            @RequestParam(required = false) String campos,

            Authentication authentication
    ) {

        return ResponseEntity.ok(
                mapper.toOutputDTO(deletarTarefa.deletarTarefa(
                        tarefaId,
                        ControllerUtils.isGestor(authentication),
                        ControllerUtils.usuarioId(authentication)
                ))
        );

    }

    // Mapper para DTO de saída em lote
    private List<TarefaOutputDTO> toOutputDTO(List<Tarefa> tarefas) {
        return tarefas.stream().map(mapper::toOutputDTO).toList();
    }

    // Aplicação do Mapper Dinâmico
    private List<TarefaOutputDTO> mask(List<TarefaOutputDTO> dtos, String campos) {
        if (dtos.isEmpty()) return List.of();

        List<String> available = DynamicFieldFilter.availableFields(dtos.getFirst());
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dtos
                .stream()
                .map(dto -> dynamicMapper.mask(dto, selected))
                .toList();
    }

    private TarefaOutputDTO mask(TarefaOutputDTO dto, String campos) {
        List<String> available = DynamicFieldFilter.availableFields(dto);
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dynamicMapper.mask(dto, selected);
    }
}
