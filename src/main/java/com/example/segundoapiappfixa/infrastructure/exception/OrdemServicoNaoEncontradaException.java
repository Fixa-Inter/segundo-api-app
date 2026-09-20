package com.example.segundoapiappfixa.infrastructure.exception;

public class OrdemServicoNaoEncontradaException extends RuntimeException {
    public OrdemServicoNaoEncontradaException() {
        super("exception.ordemServico.notFound");
    }
}
