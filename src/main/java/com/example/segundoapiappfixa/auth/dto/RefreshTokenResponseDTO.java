package com.example.segundoapiappfixa.auth.dto;

public record RefreshTokenResponseDTO(
        String token,
        String refreshToken
) { }
