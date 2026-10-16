package com.example.segundoapiappfixa.auth.dto;

public record GoogleLoginResponseDTO(
        String status,
        String email,
        String nome,
        String token
) {
    public static GoogleLoginResponseDTO authenticated(String email, String nome, String token) {
        return new GoogleLoginResponseDTO("AUTHENTICATED", email, nome, token);
    }

    public static GoogleLoginResponseDTO registrationRequired(String email, String nome) {
        return new GoogleLoginResponseDTO("REGISTRATION_REQUIRED", email, nome, null);
    }
}
