package com.example.segundoapiappfixa.adapters.controller;

import com.example.segundoapiappfixa.adapters.dto.output.LocalEndereco.LocalUnidadeOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Problema.ProblemaOutputDTO;
import com.example.segundoapiappfixa.adapters.mapper.LocalUnidadeMapper;
import com.example.segundoapiappfixa.adapters.utils.ControllerUtils;
import com.example.segundoapiappfixa.application.usecase.Problema.ListarLocalUnidade;
import com.example.segundoapiappfixa.domain.model.LocalUnidade;
import com.example.segundoapiappfixa.domain.model.Problema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/local-unidade")
@RequiredArgsConstructor
public class LocalUnidadeController {

    // UseCase
    private final ListarLocalUnidade listarLocalUnidade;

    // Mapper
    private final LocalUnidadeMapper mapper;

    @GetMapping("/local-unidade")
    public ResponseEntity<List<LocalUnidadeOutputDTO>> listarLocalEndereco(
            Authentication authentication
    ) {
        return ResponseEntity.ok(toOutputDTO(listarLocalUnidade.listar(ControllerUtils.usuarioId(authentication))));
    }

    // Mapper para DTO de saída em lote
    private List<LocalUnidadeOutputDTO> toOutputDTO(List<LocalUnidade> locaisUnidade) {
        return locaisUnidade.stream().map(mapper::toOutputDTO).toList();
    }

}
