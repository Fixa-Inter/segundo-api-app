package com.example.segundoapiappfixa.infrastructure.exception;

public class ProblemaNaoEncontradoException extends RuntimeException {
    public ProblemaNaoEncontradoException() {
        super("exception.problema.notFound");
    }
}
