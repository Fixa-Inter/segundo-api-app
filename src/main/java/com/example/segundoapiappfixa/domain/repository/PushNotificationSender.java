package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Notificacao;

public interface PushNotificationSender {
    void enviar(Notificacao notificacao);
}
