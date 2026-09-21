package com.example.segundoapiappfixa.adapters.dto.input.MarcaEquipamento;

import jakarta.validation.constraints.NotNull;

public record MarcaEquipamentoCadastrarInputDTO(
        @NotNull()
        String nome,

        @NotNull()
        String descricao
) {
}
