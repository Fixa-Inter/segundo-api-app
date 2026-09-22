package com.example.segundoapiappfixa.adapters.controller;

import com.example.segundoapiappfixa.adapters.dto.input.Ocorrencia.OcorrenciaAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.input.Ocorrencia.OcorrenciaCadastrarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Ocorrencia.OcorrenciaOutputDTO;
import com.example.segundoapiappfixa.adapters.mapper.OcorrenciaMapper;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.DynamicFieldFilter;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.OcorrenciaDynamicMapper;
import com.example.segundoapiappfixa.adapters.utils.ControllerUtils;
import com.example.segundoapiappfixa.application.usecase.Ocorrencia.AtualizarOcorrencia;
import com.example.segundoapiappfixa.application.usecase.Ocorrencia.CriarOcorrencia;
import com.example.segundoapiappfixa.application.usecase.Ocorrencia.DeletarOcorrencia;
import com.example.segundoapiappfixa.application.usecase.Ocorrencia.ListarOcorrencias;
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

    // UseCases
    private final ListarOcorrencias listarOcorrencias;
    private final CriarOcorrencia criarOcorrencia;
    private final AtualizarOcorrencia atualizarOcorrencia;
    private final DeletarOcorrencia deletarOcorrencia;

    // Mappers
    private final OcorrenciaMapper mapper;
    private final OcorrenciaDynamicMapper dynamicMapper;

    // GET
    @GetMapping("/minhas")
    public ResponseEntity<List<OcorrenciaOutputDTO>> listarMinhas(
            @RequestParam(required = false)
            String campos,

            Authentication authentication
    ) {
        return ResponseEntity.ok(mask(
                toOutputDTO(listarOcorrencias.listarMinhas(
                        ControllerUtils.usuarioId(authentication)
                )),
                campos
        ));
    }

    @GetMapping("/{ocorrenciaId}")
    public ResponseEntity<OcorrenciaOutputDTO> listarDetalhes(
            @PathVariable
            Long ocorrenciaId,

            @RequestParam(required = false)
            String campos,

            Authentication authentication
    ) {
        return ResponseEntity.ok(mask(
                mapper.toOutputDTO(listarOcorrencias.listar(
                        ocorrenciaId,
                        ControllerUtils.usuarioId(authentication)
                )),
                campos
        ));
    }

    // POST
    @PostMapping
    public ResponseEntity<OcorrenciaOutputDTO> cadastrar(
            @Valid
            @RequestBody
            OcorrenciaCadastrarInputDTO input,

            Authentication authentication
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toOutputDTO(criarOcorrencia.cadastrar(
                        input,
                        ControllerUtils.usuarioId(authentication)
                )));
    }

    // PATCH
    @PatchMapping("")
    public ResponseEntity<OcorrenciaOutputDTO> atualizar(
            @Valid
            @RequestBody
            OcorrenciaAtualizarInputDTO input,

            Authentication authentication
    ) {
        return ResponseEntity.ok(mapper.toOutputDTO(
                atualizarOcorrencia.atualizar(
                        input,
                        ControllerUtils.usuarioId(authentication)
                )
        ));
    }

    // DELETE
    @DeleteMapping("/{ocorrenciaId}")
    public ResponseEntity<OcorrenciaOutputDTO> deletar(
            @PathVariable
            Long ocorrenciaId,

            Authentication authentication
    ) {
        return ResponseEntity.ok(mapper.toOutputDTO(
                deletarOcorrencia.deletar(
                        ocorrenciaId,
                        ControllerUtils.usuarioId(authentication)
                )
        ));

    }

    // Mapper para DTO de saída em lote
    private List<OcorrenciaOutputDTO> toOutputDTO(List<Ocorrencia> ocorrencias) {
        return ocorrencias.stream().map(mapper::toOutputDTO).toList();
    }

    // Aplicação do Mapper Dinâmico
    private List<OcorrenciaOutputDTO> mask(List<OcorrenciaOutputDTO> dtos, String campos) {
        if (dtos.isEmpty()) return List.of();

        List<String> available = DynamicFieldFilter.availableFields(dtos.getFirst());
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dtos
                .stream()
                .map(dto -> dynamicMapper.mask(dto, selected))
                .toList();
    }

    private OcorrenciaOutputDTO mask(OcorrenciaOutputDTO dto, String campos) {
        List<String> available = DynamicFieldFilter.availableFields(dto);
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dynamicMapper.mask(dto, selected);
    }
}
