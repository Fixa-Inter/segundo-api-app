package com.example.segundoapiappfixa.adapters.dto.input.CategoriaEquipamento;

import jakarta.validation.constraints.NotBlank;

public record CategoriaEquipamentoCadastrarInputDTO(
        @NotBlank()
        String nome,

        @NotBlank()
        String descricao
) {
}
