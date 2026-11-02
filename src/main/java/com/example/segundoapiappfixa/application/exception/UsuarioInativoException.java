package com.example.segundoapiappfixa.application.exception;

public class UsuarioInativoException extends RuntimeException {
    public UsuarioInativoException() {
        super("Usuário inativo");
    }
}
