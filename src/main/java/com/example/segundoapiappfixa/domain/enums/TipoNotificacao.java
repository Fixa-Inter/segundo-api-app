package com.example.segundoapiappfixa.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TipoNotificacao {
    EVENTO(1),
    SOLICITACAO(2),
    ORDEM_SERVICO(3);

    private final int id;

    public static TipoNotificacao fromId(int id) {
        for (TipoNotificacao tipo : values()) {
            if (tipo.id == id) return tipo;
        }
        throw new IllegalArgumentException("Tipo de notificação inválido: " + id);
    }
}
