package com.example.segundoapiappfixa.adapters.dto.input.ModeloEquipamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModeloEquipamentoCadastrarInputDTO (
        @NotNull()
        Long id,

        @NotNull()
        Long marcaEquipamentoId,

        @NotNull(message = "validation.categoria.required")
        Long categoriaEquipamentoId,

        @NotBlank()
        String nome,

        @NotBlank()
        String descricao
) {
}
