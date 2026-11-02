package com.example.segundoapiappfixa.auth.dto;

public record LoginResponseDTO (
        String email,
        String token,
        String refreshToken
) {
}
