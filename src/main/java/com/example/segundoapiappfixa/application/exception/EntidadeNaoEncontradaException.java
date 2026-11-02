package com.example.segundoapiappfixa.application.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

    public EntidadeNaoEncontradaException(String messageKey) {
        super(messageKey);
    }
}
