package com.example.segundoapiappfixa.infrastructure.exception;

public class TarefaNaoEncontradaException extends RuntimeException {
    public TarefaNaoEncontradaException() {
        super("exception.tarefa.notFound");
    }
}
