package com.example.segundoapiappfixa.domain.model.auth;

public record TokensAutenticacao(
        String accessToken,
        String refreshToken
) { }
