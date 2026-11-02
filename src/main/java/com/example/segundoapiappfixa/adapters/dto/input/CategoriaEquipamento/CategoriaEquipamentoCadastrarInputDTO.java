package com.example.segundoapiappfixa.adapters.dto.input.CategoriaEquipamento;

import com.example.segundoapiappfixa.domain.model.input.CategoriaEquipamento.CategoriaEquipamentoCadastrarDados;

import jakarta.validation.constraints.NotBlank;

public record CategoriaEquipamentoCadastrarInputDTO(
        @NotBlank(message = "{validation.nome.required}")
        String nome,

        @NotBlank(message = "{validation.descricao.required}")
        String descricao
) {
    public CategoriaEquipamentoCadastrarDados toDomain() {
        return new CategoriaEquipamentoCadastrarDados(nome, descricao);
    }
}
