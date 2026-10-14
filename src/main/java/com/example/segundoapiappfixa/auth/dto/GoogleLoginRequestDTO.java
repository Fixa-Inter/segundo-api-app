package com.example.segundoapiappfixa.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record GoogleLoginRequestDTO(
        @NotBlank String idToken
) {
}
