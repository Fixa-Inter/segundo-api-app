package com.example.segundoapiappfixa.adapters.dto.input.Equipamento;

import jakarta.validation.constraints.NotNull;

public record EquipamentoAtualizarInputDTO (
        @NotNull(message = "{validation.equipamento.required}")
        Long id,

        Long modeloEquipamentoId,
        Long localEnderecoId,
        String codigo
) {
}
