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

    // UseCases
    private final ListarCategoriasEquipamento listarCategoriasEquipamento;
    private final CadastrarCategoriaEquipamento cadastrarCategoriaEquipamento;
    private final AtualizarCategoriaEquipamento atualizarCategoriaEquipamento;
    private final DeletarCategoriaEquipamento deletarCategoriaEquipamento;

    // Mappers
    private final CategoriaEquipamentoMapper mapper;
    private final CategoriaEquipamentoDynamicMapper dynamicMapper;

    // GET
    @GetMapping
    public ResponseEntity<List<CategoriaEquipamentoOutputDTO>> listar(
            @RequestParam(required = false)
            String campos,

            Authentication authentication
    ) {
        return ResponseEntity.ok(mask(
                toOutputDTO(listarCategoriasEquipamento.listar(
                        ControllerUtils.usuarioId(authentication)
                )),

                campos
        ));
    }

    @GetMapping("/{categoriaEquipamentoId}")
    public ResponseEntity<CategoriaEquipamentoOutputDTO> listarDetalhes(
            @PathVariable
            Long categoriaEquipamentoId,

            @RequestParam(required = false)
            String campos,

            Authentication authentication
    ) {
        return ResponseEntity.ok(mask(
                mapper.toOutputDTO(listarCategoriasEquipamento.listar(
                        categoriaEquipamentoId,
                        ControllerUtils.usuarioId(authentication)
                )),

                campos
        ));
    }

    // POST
    @PostMapping
    public ResponseEntity<CategoriaEquipamentoOutputDTO> cadastrar(
            @Valid
            @RequestBody
            CategoriaEquipamentoCadastrarInputDTO dto,

            Authentication authentication
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toOutputDTO(
                        cadastrarCategoriaEquipamento.cadastrar(dto,ControllerUtils.usuarioId(authentication))
                ));
    }

    // PATCH
    @PatchMapping
    public ResponseEntity<CategoriaEquipamentoOutputDTO> atualizar(
            @Valid
            @RequestBody
            CategoriaEquipamentoAtualizarInputDTO dto,

            Authentication authentication
    ) {
        return ResponseEntity.ok(mapper.toOutputDTO(
                        atualizarCategoriaEquipamento.atualizar(dto,ControllerUtils.usuarioId(authentication))
                ));
    }

    // DELETE
    @DeleteMapping("/{categoriaId}")
    public ResponseEntity<CategoriaEquipamentoOutputDTO> deletar(
            @PathVariable
            Long categoriaId,

            Authentication authentication
    ) {
        return ResponseEntity.ok(mapper.toOutputDTO(
                deletarCategoriaEquipamento.deletar(
                        categoriaId,
                        ControllerUtils.usuarioId(authentication)
                )
        ));
    }


    // Mapper par DTO de saída em Lote
    private List<CategoriaEquipamentoOutputDTO> toOutputDTO(List<CategoriaEquipamento> categoriasEquipamento) {
        return categoriasEquipamento.stream().map(mapper::toOutputDTO).toList();
    }

    // Aplicação do Mapper Dinâmico
    private CategoriaEquipamentoOutputDTO mask(CategoriaEquipamentoOutputDTO dto, String campos) {
        List<String> available = DynamicFieldFilter.availableFields(dto);
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dynamicMapper.mask(dto, selected);
    }

    private List<CategoriaEquipamentoOutputDTO> mask(List<CategoriaEquipamentoOutputDTO> dtos, String campos) {
        if (dtos.isEmpty()) return List.of();

        List<String> available = DynamicFieldFilter.availableFields(dtos.getFirst());
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dtos
                .stream()
                .map(dto -> dynamicMapper.mask(dto, selected))
                .toList();
    }

}
