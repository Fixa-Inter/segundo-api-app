package com.example.segundoapiappfixa.adapters.dto.input.Equipamento;

import com.example.segundoapiappfixa.domain.model.input.Equipamento.EquipamentoAtualizarDados;

import jakarta.validation.constraints.NotNull;

public record EquipamentoAtualizarInputDTO (
        @NotNull(message = "{validation.equipamento.required}")
        Long id,

        Long modeloEquipamentoId,
        Long localUnidadeId,
        String codigo
) {
    public EquipamentoAtualizarDados toDomain() {
        return new EquipamentoAtualizarDados(id, modeloEquipamentoId, localUnidadeId, codigo);
    }
}
