package com.example.segundoapiappfixa.adapters.dto.input.Aptidao;

import java.math.BigDecimal;



import jakarta.validation.constraints.NotNull;

public record AptidaoAtualizarInputDTO(
        @NotNull(message = "{validation.aptidao.required}")
        Long id,

        BigDecimal nota,
        Boolean estaAtivo
) {
}
