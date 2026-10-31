package com.example.segundoapiappfixa.infrastructure.external.notification;

import com.example.segundoapiappfixa.domain.model.Notificacao;
import com.example.segundoapiappfixa.domain.repository.NotificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;
import java.util.UUID;

/** Infraestrutura de notificações usada pelas implementações dos repositories. */
@Component
@RequiredArgsConstructor
public class NotificacaoInfrastructureService {
    private static final Logger log = LoggerFactory.getLogger(NotificacaoInfrastructureService.class);

    private final NotificacaoRepository notificacaoRepository;
    private final NotificacaoPushAsyncDispatcher pushDispatcher;

    public void notificarAposPersistencia(
            Long usuarioId,
            int tipo,
            Long referenciaId,
            String mensagem
    ) {
        if (usuarioId == null || mensagem == null || mensagem.isBlank()) return;

        Runnable dispatch = () -> persistirEEnviar(usuarioId, tipo, referenciaId, mensagem);
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override public void afterCommit() { dispatch.run(); }
            });
        } else {
            dispatch.run();
        }
    }

    private void persistirEEnviar(
            Long usuarioId,
            int tipo,
            Long referenciaId,
            String mensagem
    ) {
        Notificacao notificacao = new Notificacao(
                UUID.randomUUID().toString(),
                usuarioId,
                tipo,
                referenciaId,
                mensagem,
                LocalDateTime.now(),
                false
        );

        try {
            notificacaoRepository.save(notificacao);
        } catch (Exception exception) {
            // Notificações são best-effort e não revertem a ação de negócio já confirmada.
            log.warn("Não foi possível registrar a notificação {} no Redis.", notificacao.getId(), exception);
        }

        pushDispatcher.enviar(notificacao);
    }
}
