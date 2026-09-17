package com.example.segundoapiappfixa.adapters.controller;

import com.example.segundoapiappfixa.adapters.dto.output.OrdemServico.OrdemServicoDetalhesOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.OrdemServico.OrdemServicoOutputDTO;
import com.example.segundoapiappfixa.adapters.mapper.OrdemServicoMapper;
import com.example.segundoapiappfixa.application.usecase.OrdemServico.DeletarOrdemServico;
import com.example.segundoapiappfixa.application.usecase.OrdemServico.ListarOrdensServico;
import com.example.segundoapiappfixa.auth.dto.AuthenticatedUser;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/os")
@RequiredArgsConstructor
public class OrdemServicoController {

    private final OrdemServicoMapper ordemServicoMapper;
    private final ListarOrdensServico listarOrdensServico;
    private final DeletarOrdemServico deletarOrdemServico;

    @GetMapping
    public ResponseEntity<List<OrdemServicoOutputDTO>> listarOrdensServico(
            Authentication authentication
    ) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        return ResponseEntity.ok(
                toOutputDTO(listarOrdensServico.listarOrdensServico(authenticatedUser.id()))
        );
    }

    @GetMapping("/minhas")
    public ResponseEntity<List<OrdemServicoOutputDTO>> listarOrdensServicoPeloUsuario(
            Authentication authentication
    ) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        return ResponseEntity.ok(
                toOutputDTO(listarOrdensServico.listarOrdensServicoPeloUsuario(authenticatedUser.id()))
        );
    }

    @DeleteMapping("/{ordemServicoId}")
    public ResponseEntity<OrdemServicoDetalhesOutputDTO> deletarOrdemServico(
            @PathVariable
            Long ordemServicoId,

            Authentication authentication
    ) {

        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        boolean isGestor = authentication
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_GESTOR"));

        return ResponseEntity.ok(
                deletarOrdemServico.deletarOrdemServico(ordemServicoId, isGestor, authenticatedUser.id())
        );

    }

    private List<OrdemServicoOutputDTO> toOutputDTO(List<OrdemServico> ordemServicos) {
        return ordemServicos.stream().map(ordemServicoMapper::toOutputDTO).toList();
    }
}
