package com.example.segundoapiappfixa.adapters.controller;

import com.example.segundoapiappfixa.adapters.dto.input.ObservacaoConclusao.*;
import com.example.segundoapiappfixa.adapters.dto.output.ObservacaoConclusao.ObservacaoConclusaoOutputDTO;
import com.example.segundoapiappfixa.adapters.controller.contract.ObservacaoConclusaoControllerContract;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosObservacaoConclusaoQueryParam;
import com.example.segundoapiappfixa.adapters.mapper.ObservacaoConclusaoMapper;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.DynamicFieldFilter;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.ObservacaoConclusaoDynamicMapper;
import com.example.segundoapiappfixa.adapters.utils.ControllerUtils;
import com.example.segundoapiappfixa.application.usecase.ObservacaoConclusao.*;
import com.example.segundoapiappfixa.domain.model.ObservacaoConclusao;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/observacoes-conclusao")
@RequiredArgsConstructor
public class ObservacaoConclusaoController implements ObservacaoConclusaoControllerContract {

    // UseCases
    private final CadastrarObservacaoConclusao cadastrar;
    private final AtualizarObservacaoConclusao atualizar;
    private final ListarObservacaoConclusao listar;
    private final DeletarObservacaoConclusao deletar;

    // Mapper
    private final ObservacaoConclusaoMapper mapper;
    private final ObservacaoConclusaoDynamicMapper dynamicMapper;

    // GET
    @GetMapping
    public ResponseEntity<List<ObservacaoConclusaoOutputDTO>> listar(
            @RequestParam(required = false) String campos,
            @ModelAttribute FiltrosObservacaoConclusaoQueryParam filtros,
            Authentication authentication
    ) {
        return ResponseEntity.ok(mask(
                toOutputDTO(listar.listar(ControllerUtils.usuarioId(authentication), filtros)),
                campos
        ));
    }

    @GetMapping("/minhas")
    public ResponseEntity<List<ObservacaoConclusaoOutputDTO>> listarMinhas(
            @RequestParam(required = false) String campos,
            @ModelAttribute FiltrosObservacaoConclusaoQueryParam filtros,
            Authentication authentication
    ) {
        return ResponseEntity.ok(mask(
                toOutputDTO(listar.listarMinhas(ControllerUtils.usuarioId(authentication), filtros)), campos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObservacaoConclusaoOutputDTO> listarDetalhes(
            @PathVariable Long id,
            @RequestParam(required = false) String campos,
            Authentication authentication
    ) {
        return ResponseEntity.ok(mask(
                mapper.toOutputDTO(listar.listarDetalhes(
                        id,
                        ControllerUtils.usuarioId(authentication),
                        ControllerUtils.isGestor(authentication))
                ),
                campos
        ));
    }

    // POST
    @PostMapping
    public ResponseEntity<ObservacaoConclusaoOutputDTO> criar(
            @Valid @RequestBody ObservacaoConclusaoCriarInputDTO dto,
            Authentication authentication
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toOutputDTO(cadastrar.cadastrar(
                        dto,
                        ControllerUtils.usuarioId(authentication),
                        ControllerUtils.isGestor(authentication)
                )));
    }

    // PATCH
    @PatchMapping
    public ResponseEntity<ObservacaoConclusaoOutputDTO> atualizar(
            @Valid @RequestBody ObservacaoConclusaoAtualizarInputDTO dto,
            Authentication authentication
    ) {
        return ResponseEntity.ok(mapper.toOutputDTO(atualizar.atualizar(
                dto,
                ControllerUtils.usuarioId(authentication),
                ControllerUtils.isGestor(authentication)
        )));
    }

    // GET
    @DeleteMapping("/{id}")
    public ResponseEntity<ObservacaoConclusaoOutputDTO> deletar(
            @PathVariable Long id,
            Authentication a
    ) {
        return ResponseEntity.ok(mapper.toOutputDTO(deletar.deletar(
                id,
                ControllerUtils.usuarioId(a),
                ControllerUtils.isGestor(a)
        )));
    }

    // Mapper para DTO de saída em lote
    private List<ObservacaoConclusaoOutputDTO> toOutputDTO(List<ObservacaoConclusao> values) {
        return values
                .stream()
                .map(mapper::toOutputDTO)
                .toList();
    }

    // Aplicação do Mapper Dinâmico
    private List<ObservacaoConclusaoOutputDTO> mask(
            List<ObservacaoConclusaoOutputDTO> dtos,
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

    private ObservacaoConclusaoOutputDTO mask(
            ObservacaoConclusaoOutputDTO dto,
            String campos
    ) {
        List<String> available = DynamicFieldFilter.availableFields(dto);
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dynamicMapper.mask(dto, selected);
    }
}
