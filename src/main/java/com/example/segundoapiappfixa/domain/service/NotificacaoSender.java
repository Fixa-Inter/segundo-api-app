package com.example.segundoapiappfixa.domain.service;

/** Agenda o registro e o envio da notificação após a confirmação da operação. */
public interface NotificacaoSender {
    void notificarAposPersistencia(
            Long usuarioId,
            int tipo,
            Long referenciaId,
            String mensagem
    );
}
