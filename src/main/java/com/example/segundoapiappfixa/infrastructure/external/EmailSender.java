package com.example.segundoapiappfixa.infrastructure.external;

public interface EmailSender {

    void enviarCodigo(
            String recipient,
            String nome,
            String codigo,
            String validade
    );

}
