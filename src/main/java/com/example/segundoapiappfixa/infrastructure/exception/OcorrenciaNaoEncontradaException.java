package com.example.segundoapiappfixa.infrastructure.exception;

public class OcorrenciaNaoEncontradaException extends RuntimeException {

    public OcorrenciaNaoEncontradaException() {
        super("Ocorrência não encontrada");
    }
}
