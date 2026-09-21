package com.example.segundoapiappfixa.adapters.dto.input.MarcaEquipamento;

import jakarta.validation.constraints.NotNull;

public record MarcaEquipamentoAtualizarInputDTO(
        @NotNull
        Long id,

        String nome,
        String descricao,
        Boolean estaAtivo
) {
}
