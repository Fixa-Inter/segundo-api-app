package com.example.segundoapiappfixa.adapters.dto.input.MarcaEquipamento;

import jakarta.validation.constraints.NotNull;

public record MarcaEquipamentoCadastrarInputDTO(
        @NotNull(message = "{validation.nome.required}")
        String nome,

        @NotNull(message = "{validation.descricao.required}")
        String descricao
) {
}
