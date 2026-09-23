package com.example.segundoapiappfixa.adapters.dto.input.Aptidao;

import jakarta.validation.constraints.NotNull;

public record AptidaoAtualizarInputDTO(
        @NotNull(message = "{validation.aptidao.required}")
        Long id,

        Double nota,
        Boolean estaAtivo
) {
}
