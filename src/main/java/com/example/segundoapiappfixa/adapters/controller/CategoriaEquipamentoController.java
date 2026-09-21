package com.example.segundoapiappfixa.adapters.controller;

import com.example.segundoapiappfixa.adapters.dto.input.CategoriaEquipamento.CategoriaEquipamentoAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.input.CategoriaEquipamento.CategoriaEquipamentoCadastrarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.CategoriaEquipamento.CategoriaEquipamentoOutputDTO;
import com.example.segundoapiappfixa.adapters.mapper.CategoriaEquipamentoMapper;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.CategoriaEquipamentoDynamicMapper;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.DynamicFieldFilter;
import com.example.segundoapiappfixa.adapters.utils.ControllerUtils;
import com.example.segundoapiappfixa.application.usecase.CategoriaEquipamento.ListarCategoriasEquipamento;
import com.example.segundoapiappfixa.application.usecase.CategoriaEquipamento.CadastrarCategoriaEquipamento;
import com.example.segundoapiappfixa.application.usecase.CategoriaEquipamento.AtualizarCategoriaEquipamento;
import com.example.segundoapiappfixa.application.usecase.CategoriaEquipamento.DeletarCategoriaEquipamento;
import com.example.segundoapiappfixa.domain.model.CategoriaEquipamento;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipamentos/categorias")
@RequiredArgsConstructor
public class CategoriaEquipamentoController {

    private final ListarCategoriasEquipamento listarCategoriasEquipamento;
    private final CadastrarCategoriaEquipamento cadastrarCategoriaEquipamento;
    private final AtualizarCategoriaEquipamento atualizarCategoriaEquipamento;
    private final DeletarCategoriaEquipamento deletarCategoriaEquipamento;
    private final CategoriaEquipamentoMapper categoriaEquipamentoMapper;
    private final CategoriaEquipamentoDynamicMapper categoriaEquipamentoDynamicMapper;

    @GetMapping
    public ResponseEntity<List<CategoriaEquipamentoOutputDTO>> listar(
            @RequestParam(required = false)
            String campos,

            Authentication authentication
    ) {
        return ResponseEntity.ok(mask(
                toOutputDTO(listarCategoriasEquipamento.listarCategoriasEquipamento(
                        ControllerUtils.usuarioId(authentication)
                )),

                campos
        ));
    }

    @PostMapping
    public ResponseEntity<CategoriaEquipamentoOutputDTO> cadastrar(
            @Valid
            @RequestBody
            CategoriaEquipamentoCadastrarInputDTO dto,

            Authentication authentication
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoriaEquipamentoMapper.toOutputDTO(
                        cadastrarCategoriaEquipamento.cadastrar(dto,ControllerUtils.usuarioId(authentication))
                ));
    }

    @PatchMapping
    public ResponseEntity<CategoriaEquipamentoOutputDTO> atualizar(
            @Valid
            @RequestBody
            CategoriaEquipamentoAtualizarInputDTO dto,

            Authentication authentication
    ) {
        return ResponseEntity.ok(categoriaEquipamentoMapper.toOutputDTO(
                        atualizarCategoriaEquipamento.atualizar(dto,ControllerUtils.usuarioId(authentication))
                ));
    }

    @DeleteMapping("/{categoriaId}")
    public ResponseEntity<CategoriaEquipamentoOutputDTO> deletar(
            @PathVariable
            Long categoriaId,

            Authentication authentication
    ) {
        return ResponseEntity.ok(categoriaEquipamentoMapper.toOutputDTO(
                deletarCategoriaEquipamento.deletar(
                        categoriaId,
                        ControllerUtils.usuarioId(authentication)
                )
        ));
    }


    private List<CategoriaEquipamentoOutputDTO> toOutputDTO(List<CategoriaEquipamento> categoriasEquipamento) {
        return categoriasEquipamento.stream().map(categoriaEquipamentoMapper::toOutputDTO).toList();
    }

    private List<CategoriaEquipamentoOutputDTO> mask(List<CategoriaEquipamentoOutputDTO> dtos, String campos) {
        if (dtos.isEmpty()) return List.of();

        List<String> available = DynamicFieldFilter.availableFields(dtos.getFirst());
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dtos
                .stream()
                .map(dto -> categoriaEquipamentoDynamicMapper.mask(dto, selected))
                .toList();
    }

}
