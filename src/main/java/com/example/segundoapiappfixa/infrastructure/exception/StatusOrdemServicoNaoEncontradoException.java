package com.example.segundoapiappfixa.infrastructure.exception;

public class StatusOrdemServicoNaoEncontradoException extends RuntimeException {
    public StatusOrdemServicoNaoEncontradoException() {
        super("exception.statusOrdemServico.notFound");
    }
}
