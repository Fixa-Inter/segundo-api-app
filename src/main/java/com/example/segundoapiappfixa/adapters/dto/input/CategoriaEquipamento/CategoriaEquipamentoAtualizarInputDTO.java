package com.example.segundoapiappfixa.adapters.dto.input.CategoriaEquipamento;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CategoriaEquipamentoAtualizarInputDTO(
        @NotNull(message = "validation.categoriaEquipamento.required")
        Long id,

        String nome,
        String descricao,
        Boolean estaAtivo
) {
}
