package com.example.segundoapiappfixa.adapters.dto.input.Aptidao;

import com.example.segundoapiappfixa.domain.model.input.Aptidao.AptidaoAtualizarDados;

import java.math.BigDecimal;



import jakarta.validation.constraints.NotNull;

public record AptidaoAtualizarInputDTO(
        @NotNull(message = "{validation.aptidao.required}")
        Long id,

        BigDecimal nota,
        Boolean estaAtivo
) {
    public AptidaoAtualizarDados toDomain() {
        return new AptidaoAtualizarDados(id, nota, estaAtivo);
    }
}
