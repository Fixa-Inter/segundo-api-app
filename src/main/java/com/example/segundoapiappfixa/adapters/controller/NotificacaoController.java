package com.example.segundoapiappfixa.adapters.controller;

import com.example.segundoapiappfixa.adapters.dto.input.Notificacao.TokenDispositivoInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Notificacao.NotificacaoOutputDTO;
import com.example.segundoapiappfixa.adapters.mapper.NotificacaoMapper;
import com.example.segundoapiappfixa.adapters.utils.ControllerUtils;
import com.example.segundoapiappfixa.application.usecase.Notificacao.*;
import com.example.segundoapiappfixa.domain.model.Notificacao;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificacao")
@RequiredArgsConstructor
public class NotificacaoController {

    // UseCase
    private final ListarNotificacoes listar;
    private final DeletarNotificacao deletar;
    private final MarcarNotificacaoVisualizada marcarVisualizada;
    private final RegistrarTokenDispositivo registrarToken;
    private final RemoverTokenDispositivo removerToken;

    // Mapper
    private final NotificacaoMapper mapper;

    @GetMapping
    public ResponseEntity<List<NotificacaoOutputDTO>> listar(Authentication authentication) {
        return ResponseEntity.ok(toOutputDTO(listar.listar(ControllerUtils.usuarioId(authentication))));
    }

    @PatchMapping("/{notificacaoId}/visualizada")
    public ResponseEntity<NotificacaoOutputDTO> marcarVisualizada(
            @PathVariable String notificacaoId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(mapper.toOutputDTO(marcarVisualizada.marcar(
                ControllerUtils.usuarioId(authentication), notificacaoId)));
    }

    @DeleteMapping("/{notificacaoId}")
    public ResponseEntity<Void> deletar(
            @PathVariable
            String notificacaoId, Authentication authentication
    ) {
        deletar.deletar(ControllerUtils.usuarioId(authentication), notificacaoId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/dispositivo")
    public ResponseEntity<Void> registrarDispositivo(
            @Valid @RequestBody
            TokenDispositivoInputDTO input,

            Authentication authentication
    ) {
        registrarToken.registrar(ControllerUtils.usuarioId(authentication), input.token());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/dispositivo")
    public ResponseEntity<Void> removerDispositivo(
            @RequestParam
            String token,

            Authentication authentication
    ) {
        removerToken.remover(ControllerUtils.usuarioId(authentication), token);

        return ResponseEntity.noContent().build();
    }

    // Mapper para DTO de saída em lote
    private List<NotificacaoOutputDTO> toOutputDTO(List<Notificacao> notificacoes) {
        return notificacoes.stream()
                .map(mapper::toOutputDTO)
                .toList();
    }

}
