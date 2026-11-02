package com.example.segundoapiappfixa.domain.service.auth;

public interface EmailRecuperacaoSender {
    void enviarCodigo(String destinatario, String nome, String codigo, String validade);
}
