package com.example.segundoapiappfixa.adapters.controller;

import com.example.segundoapiappfixa.adapters.dto.input.ModeloEquipamento.ModeloEquipamentoAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.input.ModeloEquipamento.ModeloEquipamentoCadastrarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.ModeloEquipamento.ModeloEquipamentoOutputDTO;
import com.example.segundoapiappfixa.adapters.mapper.ModeloEquipamentoMapper;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.DynamicFieldFilter;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.ModeloEquipamentoDynamicMapper;
import com.example.segundoapiappfixa.adapters.utils.ControllerUtils;
import com.example.segundoapiappfixa.application.usecase.ModeloEquipamento.*;
import com.example.segundoapiappfixa.domain.model.ModeloEquipamento;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/equipamentos/modelos")
@RequiredArgsConstructor
public class ModeloEquipamentoController {

    private final ListarModelosEquipamentos listarModelosEquipamentos;
    private final CadastrarModeloEquipamento cadastrarModeloEquipamento;
    private final AtualizarModeloEquipamento atualizarModeloEquipamento;
    private final DeletarModeloEquipamento deletarModeloEquipamento;

    private final ModeloEquipamentoMapper mapper;
    private final ModeloEquipamentoDynamicMapper dynamicMapper;

    @GetMapping
    public ResponseEntity<List<ModeloEquipamentoOutputDTO>> listar(
            @RequestParam(required = false)
            String campos,

            Authentication authentication
    ) {
        return ResponseEntity.ok(mask(
                toOutputDTO(listarModelosEquipamentos.listar(ControllerUtils.usuarioId(authentication)))
                , campos
        ));
    }

    @GetMapping("/{modeloEquipamentoId}")
    public ResponseEntity<ModeloEquipamentoOutputDTO> listarPeloId(
            @PathVariable
            Long modeloEquipamentoId,

            @RequestParam(required = false)
            String campos,

            Authentication authentication
    ) {
        return ResponseEntity.ok(mask(
                mapper.toOutputDTO(listarModelosEquipamentos.listar(
                        modeloEquipamentoId,
                        ControllerUtils.usuarioId(authentication))
                ), campos));
    }

    @PostMapping
    public ResponseEntity<ModeloEquipamentoOutputDTO> cadastrar(
            @Valid
            @RequestBody
            ModeloEquipamentoCadastrarInputDTO dto,

            Authentication authentication
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toOutputDTO(cadastrarModeloEquipamento.cadastrar(
                        dto,
                        ControllerUtils.usuarioId(authentication))
                ));
    }

    @PatchMapping
    public ResponseEntity<ModeloEquipamentoOutputDTO> atualizar(
            @Valid
            @RequestBody
            ModeloEquipamentoAtualizarInputDTO dto,

            Authentication authentication
    ) {
        return ResponseEntity.ok(mapper.toOutputDTO(atualizarModeloEquipamento.atualizar(
                dto,
                ControllerUtils.usuarioId(authentication)
        )));
    }

    @DeleteMapping("/{modeloEquipamentoId}")
    public ResponseEntity<ModeloEquipamentoOutputDTO> deletar(
            @PathVariable
            Long modeloEquipamentoId,

            Authentication authentication
    ) {
        return ResponseEntity.ok(mapper.toOutputDTO(deletarModeloEquipamento.deletar(
                modeloEquipamentoId,
                ControllerUtils.usuarioId(authentication)
        )));
    }

    private List<ModeloEquipamentoOutputDTO> toOutputDTO(List<ModeloEquipamento> modelos) {
        return modelos.stream().map(mapper::toOutputDTO).toList();
    }

    private List<ModeloEquipamentoOutputDTO> mask(List<ModeloEquipamentoOutputDTO> dtos, String campos) {
        if (dtos.isEmpty()) return List.of();

        List<String> available = DynamicFieldFilter.availableFields(dtos.getFirst());
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dtos
                .stream()
                .map(dto -> dynamicMapper.mask(dto, selected))
                .toList();
    }

    private ModeloEquipamentoOutputDTO mask(ModeloEquipamentoOutputDTO dto, String campos) {

        List<String> available = DynamicFieldFilter.availableFields(dto);
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dynamicMapper.mask(dto, selected);
    }
}
