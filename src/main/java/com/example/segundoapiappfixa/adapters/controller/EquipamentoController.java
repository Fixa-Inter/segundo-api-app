package com.example.segundoapiappfixa.adapters.controller;

import com.example.segundoapiappfixa.adapters.dto.input.Equipamento.EquipamentoAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.input.Equipamento.EquipamentoCadastrarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Equipamento.EquipamentoOutputDTO;
import com.example.segundoapiappfixa.adapters.mapper.EquipamentoMapper;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.DynamicFieldFilter;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.EquipamentoDynamicMapper;
import com.example.segundoapiappfixa.adapters.utils.ControllerUtils;
import com.example.segundoapiappfixa.application.usecase.Equipamento.AtualizarEquipamento;
import com.example.segundoapiappfixa.application.usecase.Equipamento.CadastrarEquipamento;
import com.example.segundoapiappfixa.application.usecase.Equipamento.DeletarEquipamento;
import com.example.segundoapiappfixa.application.usecase.Equipamento.ListarEquipamentos;
import com.example.segundoapiappfixa.domain.model.Equipamento;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipamentos")
@RequiredArgsConstructor
public class EquipamentoController {

    // UseCases
    private final ListarEquipamentos listarEquipamentos;
    private final CadastrarEquipamento cadastrarEquipamento;
    private final AtualizarEquipamento atualizarEquipamento;
    private final DeletarEquipamento deletarEquipamento;

    // Mappers
    private final EquipamentoMapper mapper;
    private final EquipamentoDynamicMapper dynamicMapper;

    // GET
    @GetMapping("/modelo/{modeloEquipamentoId}")
    public ResponseEntity<List<EquipamentoOutputDTO>> listar(
            @PathVariable
            Long modeloEquipamentoId,

            @RequestParam(required = false)
            String campos,

            Authentication authentication
    ) {
        return ResponseEntity.ok(mask(
                toOutputDTO(listarEquipamentos.listar(
                        modeloEquipamentoId,
                        ControllerUtils.usuarioId(authentication)
                )),
                campos
        ));
    }

    @GetMapping("/{equipamentoId}")
    public ResponseEntity<EquipamentoOutputDTO> listarDetalhes(
            @PathVariable
            Long equipamentoId,

            @RequestParam(required = false)
            String campos,

            Authentication authentication
    ) {
        return ResponseEntity.ok(mask(
                mapper.toOutputDTO(listarEquipamentos.listarDetalhes(
                        equipamentoId,
                        ControllerUtils.usuarioId(authentication)
                )),
                campos
        ));
    }

    // POST
    @PostMapping
    public ResponseEntity<EquipamentoOutputDTO> cadastrar(
            @Valid
            @RequestBody
            EquipamentoCadastrarInputDTO dto,

            Authentication authentication
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toOutputDTO(cadastrarEquipamento.cadastrar(
                        dto,
                        ControllerUtils.usuarioId(authentication)
                )
        ));
    }

    // PATCH
    @PatchMapping
    public ResponseEntity<EquipamentoOutputDTO> atualizar(
            @Valid
            @RequestBody
            EquipamentoAtualizarInputDTO dto,

            Authentication authentication
    ) {
        return ResponseEntity.ok(mapper.toOutputDTO(atualizarEquipamento.atualizar(
                        dto,
                        ControllerUtils.usuarioId(authentication)
                ))
        );
    }

    // DELETE
    @DeleteMapping("/{equipamentoId}")
    public ResponseEntity<EquipamentoOutputDTO> deletar(
            @PathVariable
            Long equipamentoId,

            Authentication authentication
    ) {
        return ResponseEntity.ok(mapper.toOutputDTO(
                deletarEquipamento.deletar(
                        equipamentoId,
                        ControllerUtils.usuarioId(authentication)
                )
        ));
    }

    // Mapper para DTO de saída em lote
    private List<EquipamentoOutputDTO> toOutputDTO(List<Equipamento> equipamentos) {
        return equipamentos
                .stream()
                .map(mapper::toOutputDTO)
                    .toList();
    }

    // Aplicação do Mapper Dinâmico
    private EquipamentoOutputDTO mask(EquipamentoOutputDTO dto, String campos) {

        List<String> available = DynamicFieldFilter.availableFields(dto);
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dynamicMapper.mask(dto, selected);
    }

    private List<EquipamentoOutputDTO> mask(List<EquipamentoOutputDTO> dtos, String campos) {
        if (dtos.isEmpty()) return List.of();

        List<String> available = DynamicFieldFilter.availableFields(dtos.getFirst());
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dtos
                .stream()
                .map(dto -> dynamicMapper.mask(dto, selected))
                .toList();
    }
}
