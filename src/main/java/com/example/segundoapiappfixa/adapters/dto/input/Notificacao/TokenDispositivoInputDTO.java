package com.example.segundoapiappfixa.adapters.dto.input.Notificacao;

import jakarta.validation.constraints.NotBlank;

public record TokenDispositivoInputDTO(
        @NotBlank
        String token
) {
}
