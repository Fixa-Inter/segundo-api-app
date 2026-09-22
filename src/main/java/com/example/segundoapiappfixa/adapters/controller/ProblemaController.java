package com.example.segundoapiappfixa.adapters.controller;

import com.example.segundoapiappfixa.adapters.dto.input.Problema.ProblemaAtualizarStatusDTO;
import com.example.segundoapiappfixa.adapters.dto.input.Problema.ProblemaCriarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Problema.ProblemaDetalhesOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Problema.ProblemaOutputDTO;
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
public class ProblemaController {

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

            Authentication authentication
    ) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        return ResponseEntity.ok(
                mask(toOutputDTO(listarProblemas.listar(authenticatedUser.id())), campos)
        );
    }

    @GetMapping("/minhas")
    public ResponseEntity<List<ProblemaOutputDTO>> listarMinhas(
            @RequestParam(required = false)
            String campos,

            Authentication authentication
    ) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        return ResponseEntity.ok(
                mask(toOutputDTO(listarProblemas.listarMinhas(authenticatedUser.id())), campos)
        );
    }

    @GetMapping("/selecionar/{problemaId}")
    public ResponseEntity<ProblemaDetalhesOutputDTO> listarDetalhes(
            @PathVariable
            Long problemaId,

            Authentication authentication
    ) {

        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        boolean isGestor = authentication
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_GESTOR"));

        return ResponseEntity.ok(
                listarProblemas.listar(
                        problemaId, authenticatedUser.id(), isGestor)
        );
    }

    // POST
    @PostMapping
    public ResponseEntity<ProblemaDetalhesOutputDTO> cadastrar(
            @Valid @RequestBody
            ProblemaCriarInputDTO input,

            Authentication authentication
    ) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cadastrarProblema.cadastrar(input, authenticatedUser.id()));
    }

    // PATCH
    @PatchMapping("/status")
    public ResponseEntity<ProblemaDetalhesOutputDTO> atualizaStatus(
            @Valid @RequestBody
            ProblemaAtualizarStatusDTO input,

            Authentication authentication
    ) {
        boolean isGestor = authentication.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_GESTOR"));

        return ResponseEntity.ok(atualizarStatus.atualizar(input, isGestor));
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
}
