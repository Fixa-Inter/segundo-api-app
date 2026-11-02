package com.example.segundoapiappfixa.adapters.dto.input.ModeloEquipamento;

import com.example.segundoapiappfixa.domain.model.input.ModeloEquipamento.ModeloEquipamentoAtualizarDados;

import jakarta.validation.constraints.NotNull;

public record ModeloEquipamentoAtualizarInputDTO(
        @NotNull(message = "{validation.modeloEquipamento.required}")
        Long id,

        Long marcaEquipamentoId,
        Long categoriaEquipamentoId,
        String nome,
        String descricao,
        Boolean estaAtivo
) {
    public ModeloEquipamentoAtualizarDados toDomain() {
        return new ModeloEquipamentoAtualizarDados(id, marcaEquipamentoId, categoriaEquipamentoId, nome, descricao, estaAtivo);
    }
}
