package com.example.segundoapiappfixa.infrastructure.external.notification;

import com.example.segundoapiappfixa.domain.model.Notificacao;
import com.example.segundoapiappfixa.domain.repository.PushNotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificacaoPushAsyncDispatcher {
    private final PushNotificationSender pushNotificationSender;

    @Async
    public void enviar(Notificacao notificacao) {
        pushNotificationSender.enviar(notificacao);
    }
}
