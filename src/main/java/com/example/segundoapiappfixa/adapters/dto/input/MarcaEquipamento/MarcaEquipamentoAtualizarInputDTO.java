package com.example.segundoapiappfixa.adapters.dto.input.MarcaEquipamento;

import com.example.segundoapiappfixa.domain.model.input.MarcaEquipamento.MarcaEquipamentoAtualizarDados;

import jakarta.validation.constraints.NotNull;

public record MarcaEquipamentoAtualizarInputDTO(
        @NotNull(message = "{validation.marcaEquipamento.required}")
        Long id,

        String nome,
        String descricao,
        Boolean estaAtivo
) {
    public MarcaEquipamentoAtualizarDados toDomain() {
        return new MarcaEquipamentoAtualizarDados(id, nome, descricao, estaAtivo);
    }
}
