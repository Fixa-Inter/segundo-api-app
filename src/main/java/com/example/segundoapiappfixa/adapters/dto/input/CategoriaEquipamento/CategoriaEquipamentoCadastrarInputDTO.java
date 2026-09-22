package com.example.segundoapiappfixa.adapters.dto.input.CategoriaEquipamento;

import jakarta.validation.constraints.NotBlank;

public record CategoriaEquipamentoCadastrarInputDTO(
        @NotBlank(message = "{validation.nome.required}")
        String nome,

        @NotBlank(message = "{validation.descricao.required}")
        String descricao
) {
}
