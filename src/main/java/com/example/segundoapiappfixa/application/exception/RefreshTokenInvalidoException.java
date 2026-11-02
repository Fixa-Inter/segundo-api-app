package com.example.segundoapiappfixa.application.exception;

public class RefreshTokenInvalidoException extends RuntimeException {
    public RefreshTokenInvalidoException() {
        super("exception.auth.refreshToken.invalid");
    }
}
