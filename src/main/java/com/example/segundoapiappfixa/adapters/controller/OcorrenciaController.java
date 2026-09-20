package com.example.segundoapiappfixa.adapters.controller;

import com.example.segundoapiappfixa.adapters.dto.input.Ocorrencia.OcorrenciaAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.input.Ocorrencia.OcorrenciaCadastrarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Ocorrencia.OcorrenciaOutputDTO;
import com.example.segundoapiappfixa.adapters.mapper.OcorrenciaMapper;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.DynamicFieldFilter;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.OcorrenciaDynamicMapper;
import com.example.segundoapiappfixa.adapters.utils.ControllerUtils;
import com.example.segundoapiappfixa.application.usecase.Ocorrencia.*;
import com.example.segundoapiappfixa.domain.model.Ocorrencia;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ocorrencias")
@RequiredArgsConstructor
public class OcorrenciaController {

    private final ListarOcorrencias listarOcorrencias;
    private final CriarOcorrencia criarOcorrencia;
    private final AtualizarOcorrencia atualizarOcorrencia;
    private final DeletarOcorrencia deletarOcorrencia;
    private final OcorrenciaMapper ocorrenciaMapper;
    private final OcorrenciaDynamicMapper ocorrenciaDynamicMapper;

    @GetMapping("/minhas")
    public ResponseEntity<List<OcorrenciaOutputDTO>> listarMinhas(
            @RequestParam(required = false)
            String campos,

            Authentication authentication
    ) {
        List<OcorrenciaOutputDTO> dtos = listarOcorrencias
                .listarMinhas(ControllerUtils.usuarioId(authentication))
                .stream()
                .map(ocorrenciaMapper::toOutputDTO)
                .toList();

        return ResponseEntity.ok(mask(dtos, campos));
    }

    @GetMapping("/{ocorrenciaId}")
    public ResponseEntity<OcorrenciaOutputDTO> listarDetalhes(
            @PathVariable
            Long ocorrenciaId,

            @RequestParam(required = false)
            String campos,

            Authentication authentication
    ) {
        OcorrenciaOutputDTO dto = ocorrenciaMapper
                .toOutputDTO(listarOcorrencias.listar(ocorrenciaId, ControllerUtils.usuarioId(authentication)));

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<OcorrenciaOutputDTO> cadastrar(
            @Valid
            @RequestBody
            OcorrenciaCadastrarInputDTO input,

            Authentication authentication
    ) {
        OcorrenciaOutputDTO dto = ocorrenciaMapper
                .toOutputDTO(criarOcorrencia.criar(input, ControllerUtils.usuarioId(authentication)));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dto);
    }

    @PatchMapping("")
    public ResponseEntity<OcorrenciaOutputDTO> atualizar(
            @Valid
            @RequestBody
            OcorrenciaAtualizarInputDTO input,

            Authentication authentication
    ) {
        OcorrenciaOutputDTO dto = ocorrenciaMapper.toOutputDTO(
                atualizarOcorrencia.atualizar(input, ControllerUtils.usuarioId(authentication)));

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{ocorrenciaId}")
    public ResponseEntity<OcorrenciaOutputDTO> deletar(
            @PathVariable
            Long ocorrenciaId,

            Authentication authentication
    ) {
        OcorrenciaOutputDTO dto = ocorrenciaMapper.toOutputDTO(
                deletarOcorrencia.deletar(ocorrenciaId, ControllerUtils.usuarioId(authentication)));

        return ResponseEntity.ok(dto);
    }

    private List<OcorrenciaOutputDTO> mask(List<OcorrenciaOutputDTO> dtos, String campos) {
        if (dtos.isEmpty()) return List.of();

        List<String> available = DynamicFieldFilter.availableFields(dtos.getFirst());
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dtos
                .stream()
                .map(dto -> ocorrenciaDynamicMapper.mask(dto, selected))
                .toList();
    }
}
