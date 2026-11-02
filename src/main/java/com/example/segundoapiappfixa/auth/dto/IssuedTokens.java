package com.example.segundoapiappfixa.auth.dto;

public record IssuedTokens(
        String accessToken,
        String refreshToken
) { }
