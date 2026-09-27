package com.example.segundoapiappfixa.adapters.controller;

import com.example.segundoapiappfixa.adapters.dto.input.Problema.ProblemaAtualizarStatusDTO;
import com.example.segundoapiappfixa.adapters.dto.input.Problema.ProblemaCriarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Problema.ProblemaOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosProblemaQueryParam;
import com.example.segundoapiappfixa.adapters.controller.contract.ProblemaControllerContract;
import com.example.segundoapiappfixa.adapters.utils.ControllerUtils;
import com.example.segundoapiappfixa.application.usecase.Problema.AtualizarStatus;
import com.example.segundoapiappfixa.application.usecase.Problema.CriarProblema;
import com.example.segundoapiappfixa.application.usecase.Problema.ListarProblemas;
import com.example.segundoapiappfixa.adapters.mapper.ProblemaMapper;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.DynamicFieldFilter;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.ProblemaDynamicMapper;
import com.example.segundoapiappfixa.auth.dto.AuthenticatedUser;
import com.example.segundoapiappfixa.domain.model.Problema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/solicitacoes")
@RequiredArgsConstructor
public class ProblemaController implements ProblemaControllerContract {

    // UseCases
    private final ListarProblemas listarProblemas;
    private final CriarProblema cadastrarProblema;
    private final AtualizarStatus atualizarStatus;

    // Mappers
    private final ProblemaMapper mapper;
    private final ProblemaDynamicMapper dynamicMapper;

    // GET
    @GetMapping
    public ResponseEntity<List<ProblemaOutputDTO>> listar(
            @RequestParam(required = false)
            String campos,

            @ModelAttribute
            FiltrosProblemaQueryParam filtros,

            Authentication authentication
    ) {
        return ResponseEntity.ok(
                mask(toOutputDTO(listarProblemas.listar(
                        ControllerUtils.usuarioId(authentication), filtros
                )), campos)
        );
    }

    @GetMapping("/minhas")
    public ResponseEntity<List<ProblemaOutputDTO>> listarMinhas(
            @RequestParam(required = false)
            String campos,

            @ModelAttribute
            FiltrosProblemaQueryParam filtros,

            Authentication authentication
    ) {
        return ResponseEntity.ok(
                mask(toOutputDTO(listarProblemas.listarMinhas(
                        ControllerUtils.usuarioId(authentication), filtros
                )), campos)
        );
    }

    @GetMapping("/selecionar/{problemaId}")
    public ResponseEntity<ProblemaOutputDTO> listarDetalhes(
            @PathVariable
            Long problemaId,

            @RequestParam(required = false)
            String campos,

            Authentication authentication
    ) {
        ProblemaOutputDTO dto = mapper.toOutputDTO(
                listarProblemas.listar(
                        problemaId,
                        ControllerUtils.usuarioId(authentication),
                        ControllerUtils.isGestor(authentication)
                )
        );

        return ResponseEntity.ok(mask(dto, campos));
    }

    // POST
    @PostMapping
    public ResponseEntity<ProblemaOutputDTO> cadastrar(
            @Valid @RequestBody
            ProblemaCriarInputDTO input,

            Authentication authentication
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toOutputDTO(cadastrarProblema.cadastrar(
                        input,
                        ControllerUtils.usuarioId(authentication)
                )));
    }

    // PATCH
    @PatchMapping("/status")
    public ResponseEntity<ProblemaOutputDTO> atualizaStatus(
            @Valid @RequestBody
            ProblemaAtualizarStatusDTO input,

            Authentication authentication
    ) {
        return ResponseEntity.ok(mapper.toOutputDTO(atualizarStatus.atualizar(input)));
    }


    // Mapper para DTO de saída em lote
    private List<ProblemaOutputDTO> toOutputDTO(List<Problema> problemas) {
        return problemas.stream().map(mapper::toOutputDTO).toList();
    }

    // Aplicação do Mapper Dinâmico
    private List<ProblemaOutputDTO> mask(List<ProblemaOutputDTO> dtos, String campos) {
        if (dtos.isEmpty()) return List.of();

        List<String> available = DynamicFieldFilter.availableFields(dtos.getFirst());
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dtos
                .stream()
                .map(dto -> dynamicMapper.mask(dto, selected))
                .toList();
    }

    private ProblemaOutputDTO mask(ProblemaOutputDTO dto, String campos) {
        List<String> available = DynamicFieldFilter.availableFields(dto);
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dynamicMapper.mask(dto, selected);
    }
}
