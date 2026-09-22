package com.example.segundoapiappfixa.adapters.dto.input.MarcaEquipamento;

import jakarta.validation.constraints.NotNull;

public record MarcaEquipamentoAtualizarInputDTO(
        @NotNull(message = "{validation.marcaEquipamento.required}")
        Long id,

        String nome,
        String descricao,
        Boolean estaAtivo
) {
}
