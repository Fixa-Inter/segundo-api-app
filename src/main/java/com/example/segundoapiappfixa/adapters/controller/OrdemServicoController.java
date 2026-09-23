package com.example.segundoapiappfixa.adapters.controller;

import com.example.segundoapiappfixa.adapters.dto.output.OrdemServico.OrdemServicoDetalhesOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.OrdemServico.OrdemServicoOutputDTO;
import com.example.segundoapiappfixa.adapters.mapper.OrdemServicoMapper;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.DynamicFieldFilter;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.OrdemServicoDynamicMapper;
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

    // UseCases
    private final ListarOrdensServico listarOrdensServico;
    private final DeletarOrdemServico deletarOrdemServico;

    // Mappers
    private final OrdemServicoMapper mapper;
    private final OrdemServicoDynamicMapper dynamicMapper;

    // GET
    @GetMapping
    public ResponseEntity<List<OrdemServicoOutputDTO>> listar(
            @RequestParam(required = false) String campos,
            Authentication authentication
    ) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        return ResponseEntity.ok(
                mask(toOutputDTO(listarOrdensServico.listar(authenticatedUser.id())), campos)
        );
    }

    @GetMapping("/minhas")
    public ResponseEntity<List<OrdemServicoOutputDTO>> listarMinhas(
            @RequestParam(required = false) String campos,
            Authentication authentication
    ) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();

        return ResponseEntity.ok(
                mask(toOutputDTO(listarOrdensServico.listarMinhas(authenticatedUser.id())), campos)
        );
    }

    // DELETE
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
                deletarOrdemServico.deletar(
                        ordemServicoId,
                        isGestor,
                        authenticatedUser.id()
                )
        );

    }

    // Mapper para DTO de saída em lote
    private List<OrdemServicoOutputDTO> toOutputDTO(List<OrdemServico> ordemServicos) {
        return ordemServicos.stream().map(mapper::toOutputDTO).toList();
    }

    // Aplicação do Mapper Dinâmico
    private List<OrdemServicoOutputDTO> mask(List<OrdemServicoOutputDTO> dtos, String campos) {
        if (dtos.isEmpty()) return List.of();

        List<String> available = DynamicFieldFilter.availableFields(dtos.getFirst());
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dtos
                .stream()
                .map(dto -> dynamicMapper.mask(dto, selected))
                .toList();
    }
}
