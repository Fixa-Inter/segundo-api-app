package com.example.segundoapiappfixa.infrastructure.external.notification;

import com.example.segundoapiappfixa.domain.repository.NotificacaoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotificacaoInfrastructureServiceTest {
    @Test
    void enviaSomenteDepoisDoCommit() {
        NotificacaoRepository repository = mock(NotificacaoRepository.class);
        NotificacaoPushAsyncDispatcher dispatcher = mock(NotificacaoPushAsyncDispatcher.class);
        NotificacaoInfrastructureService service = new NotificacaoInfrastructureService(repository, dispatcher);
        TransactionSynchronizationManager.initSynchronization();
        try {
            service.notificarAposPersistencia(1L, 2, 3L, "Mensagem");
            verifyNoInteractions(repository, dispatcher);
            TransactionSynchronizationManager.getSynchronizations().forEach(TransactionSynchronization::afterCommit);
            verify(repository).save(any());
            verify(dispatcher).enviar(any());
        } finally {
            TransactionSynchronizationManager.clearSynchronization();
        }
    }

    @Test
    void naoEnviaNoRollback() {
        NotificacaoRepository repository = mock(NotificacaoRepository.class);
        NotificacaoPushAsyncDispatcher dispatcher = mock(NotificacaoPushAsyncDispatcher.class);
        NotificacaoInfrastructureService service = new NotificacaoInfrastructureService(repository, dispatcher);
        TransactionSynchronizationManager.initSynchronization();
        try {
            service.notificarAposPersistencia(1L, 2, 3L, "Mensagem");
            TransactionSynchronizationManager.getSynchronizations().forEach(
                    sync -> sync.afterCompletion(TransactionSynchronization.STATUS_ROLLED_BACK));
            verifyNoInteractions(repository, dispatcher);
        } finally {
            TransactionSynchronizationManager.clearSynchronization();
        }
    }
}
