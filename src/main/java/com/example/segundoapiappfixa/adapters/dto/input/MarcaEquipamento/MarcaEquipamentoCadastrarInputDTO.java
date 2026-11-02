package com.example.segundoapiappfixa.adapters.dto.input.MarcaEquipamento;

import com.example.segundoapiappfixa.domain.model.input.MarcaEquipamento.MarcaEquipamentoCadastrarDados;

import jakarta.validation.constraints.NotNull;

public record MarcaEquipamentoCadastrarInputDTO(
        @NotNull(message = "{validation.nome.required}")
        String nome,

        @NotNull(message = "{validation.descricao.required}")
        String descricao
) {
    public MarcaEquipamentoCadastrarDados toDomain() {
        return new MarcaEquipamentoCadastrarDados(nome, descricao);
    }
}
