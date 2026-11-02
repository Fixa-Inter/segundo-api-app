package com.example.segundoapiappfixa.domain.model.auth;

public record LoginResultado(
        String email,
        TokensAutenticacao tokens
) { }
