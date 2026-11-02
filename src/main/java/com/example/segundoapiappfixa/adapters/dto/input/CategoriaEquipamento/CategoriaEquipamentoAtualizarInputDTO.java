package com.example.segundoapiappfixa.adapters.dto.input.CategoriaEquipamento;

import com.example.segundoapiappfixa.domain.model.input.CategoriaEquipamento.CategoriaEquipamentoAtualizarDados;

import jakarta.validation.constraints.NotNull;

public record CategoriaEquipamentoAtualizarInputDTO(
        @NotNull(message = "{validation.categoriaEquipamento.required}")
        Long id,

        String nome,
        String descricao,
        Boolean estaAtivo
) {
    public CategoriaEquipamentoAtualizarDados toDomain() {
        return new CategoriaEquipamentoAtualizarDados(id, nome, descricao, estaAtivo);
    }
}
