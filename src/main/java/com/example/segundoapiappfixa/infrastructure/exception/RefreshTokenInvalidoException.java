package com.example.segundoapiappfixa.infrastructure.exception;

public class RefreshTokenInvalidoException extends RuntimeException {
    public RefreshTokenInvalidoException() {
        super("exception.auth.refreshToken.invalid");
    }
}
