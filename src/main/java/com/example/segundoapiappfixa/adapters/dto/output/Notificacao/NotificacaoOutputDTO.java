package com.example.segundoapiappfixa.adapters.dto.output.Notificacao;

import java.time.LocalDateTime;

public record NotificacaoOutputDTO(
        String id,
        Long usuarioId,
        Integer tipoNotificacao,
        Long referenciaId,
        String mensagem,
        LocalDateTime dataCriacao,
        Boolean visualizada
) { }
