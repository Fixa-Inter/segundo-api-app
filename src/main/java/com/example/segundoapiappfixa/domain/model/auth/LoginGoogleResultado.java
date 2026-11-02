package com.example.segundoapiappfixa.domain.model.auth;

public record LoginGoogleResultado(
        String status,
        String email,
        String nome,
        TokensAutenticacao tokens
) { }
