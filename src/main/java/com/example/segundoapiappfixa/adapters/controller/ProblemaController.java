package com.example.segundoapiappfixa.adapters.controller;

import com.example.segundoapiappfixa.adapters.dto.input.Problema.ProblemaAtualizarStatusDTO;
import com.example.segundoapiappfixa.adapters.dto.input.Problema.ProblemaCriarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Problema.ProblemaDetalhesOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Problema.ProblemaOutputDTO;
import com.example.segundoapiappfixa.application.usecase.Problema.AtualizarStatus;
import com.example.segundoapiappfixa.application.usecase.Problema.CriarProblema;
import com.example.segundoapiappfixa.application.usecase.Problema.ListarDetalhesProblema;
import com.example.segundoapiappfixa.application.usecase.Problema.ListarProblemas;
import com.example.segundoapiappfixa.adapters.mapper.ProblemaMapper;
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

    private final ListarProblemas listarProblemas;
    private final ListarDetalhesProblema listarDetalhesProblema;
    private final CriarProblema cadastrarProblema;
    private final AtualizarStatus atualizarStatus;
    private final ProblemaMapper problemaMapper;

    @GetMapping
    public ResponseEntity<List<ProblemaOutputDTO>> listar(
            Authentication authentication
    ) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        return ResponseEntity.ok(
                toOutputDTO(listarProblemas.listarTodosProblemas(authenticatedUser.id()))
        );
    }

    @GetMapping("/minhas")
    public ResponseEntity<List<ProblemaOutputDTO>> listarProblemasPeloUsuario(
            Authentication authentication
    ) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        return ResponseEntity.ok(
                toOutputDTO(listarProblemas.listarProblemasPeloUsuario(authenticatedUser.id()))
        );
    }

    @GetMapping("/selecionar/{problemaId}")
    public ResponseEntity<ProblemaDetalhesOutputDTO> listarDetalhesPeloId(
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
                listarDetalhesProblema.listarDetalhesPeloId(
                        problemaId, authenticatedUser.id(), isGestor)
        );
    }

    @PostMapping
    public ResponseEntity<ProblemaDetalhesOutputDTO> criarProblema(
            @Valid @RequestBody
            ProblemaCriarInputDTO input,

            Authentication authentication
    ) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cadastrarProblema.criar(input, authenticatedUser.id()));
    }

    @PatchMapping("/status")
    public ResponseEntity<ProblemaDetalhesOutputDTO> atualizarStatus(
            @Valid @RequestBody
            ProblemaAtualizarStatusDTO input,

            Authentication authentication
    ) {
        boolean isGestor = authentication.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_GESTOR"));

        return ResponseEntity.ok(atualizarStatus.atualizar(input, isGestor));
    }


    private List<ProblemaOutputDTO> toOutputDTO(List<Problema> problemas) {
        return problemas.stream().map(problemaMapper::toOutputDTO).toList();
    }
}
