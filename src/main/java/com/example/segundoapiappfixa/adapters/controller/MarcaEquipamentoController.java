package com.example.segundoapiappfixa.adapters.controller;

import com.example.segundoapiappfixa.adapters.dto.input.MarcaEquipamento.MarcaEquipamentoAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.input.MarcaEquipamento.MarcaEquipamentoCadastrarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.MarcaEquipamento.MarcaEquipamentoOutputDTO;
import com.example.segundoapiappfixa.adapters.mapper.MarcaEquipamentoMapper;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.DynamicFieldFilter;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.MarcaEquipamentoDynamicMapper;
import com.example.segundoapiappfixa.application.usecase.MarcaEquipamento.*;
import com.example.segundoapiappfixa.domain.model.MarcaEquipamento;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipamentos/marcas")
@RequiredArgsConstructor
public class MarcaEquipamentoController {

    private final ListarMarcasEquipamento listarMarcasEquipamento;
    private final CadastrarMarcaEquipamento cadastrarMarcaEquipamento;
    private final AtualizarMarcaEquipamento atualizarMarcaEquipamento;
    private final DeletarMarcaEquipamento deletarMarcaEquipamento;

    private final MarcaEquipamentoMapper mapper;
    private final MarcaEquipamentoDynamicMapper dynamicMapper;

    @GetMapping
    public ResponseEntity<List<MarcaEquipamentoOutputDTO>> listar(
            @RequestParam(required = false)
            String campos
    ) {
        return ResponseEntity.ok(mask(
                toOutputDTO(listarMarcasEquipamento.listar()),
                campos
        ));
    }

    @GetMapping("/{marcaEquipamentoId}")
    public ResponseEntity<MarcaEquipamentoOutputDTO> listarPeloId(
            @PathVariable
            Long marcaEquipamentoId,

            @RequestParam(required = false)
            String campos
    ) {
        return ResponseEntity.ok(mask(
                mapper.toOutputDTO(listarMarcasEquipamento.listar(marcaEquipamentoId)),
                campos
        ));
    }

    @PostMapping
    public ResponseEntity<MarcaEquipamentoOutputDTO> cadastrar(
            @Valid
            @RequestBody
            MarcaEquipamentoCadastrarInputDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toOutputDTO(cadastrarMarcaEquipamento.cadastrar(dto))
        );
    }

    @PatchMapping
    public ResponseEntity<MarcaEquipamentoOutputDTO> atualizar(
            @Valid
            @RequestBody MarcaEquipamentoAtualizarInputDTO dto
    ) {
        return ResponseEntity.ok(
                mapper.toOutputDTO(atualizarMarcaEquipamento.atualizar(dto))
        );
    }

    @DeleteMapping("/{marcaEquipamentoId}")
    public ResponseEntity<MarcaEquipamentoOutputDTO> deletar(
            @PathVariable
            Long marcaEquipamentoId
    ) {
        return ResponseEntity.ok(
                mapper.toOutputDTO(deletarMarcaEquipamento.deletar(marcaEquipamentoId))
        );
    }

    private List<MarcaEquipamentoOutputDTO> toOutputDTO(List<MarcaEquipamento> marcasEquipamento) {
        return marcasEquipamento
                .stream()
                .map(mapper::toOutputDTO)
                .toList();
    }

    private List<MarcaEquipamentoOutputDTO> mask(
            List<MarcaEquipamentoOutputDTO> dtos,
            String campos
    ) {
        if (dtos.isEmpty()) return List.of();

        List<String> available = DynamicFieldFilter.availableFields(dtos.getFirst());
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dtos
                .stream()
                .map(dto -> dynamicMapper.mask(dto, selected))
                .toList();
    }

    private MarcaEquipamentoOutputDTO mask(
            MarcaEquipamentoOutputDTO dto,
            String campos
    ) {
        List<String> available = DynamicFieldFilter.availableFields(dto);
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dynamicMapper.mask(dto, selected);
    }
}
