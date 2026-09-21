package com.example.segundoapiappfixa.infrastructure.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

    public EntidadeNaoEncontradaException(String messageKey) {
        super(messageKey);
    }
}
