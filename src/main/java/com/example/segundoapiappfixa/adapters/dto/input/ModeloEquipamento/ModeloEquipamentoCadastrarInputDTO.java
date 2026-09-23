package com.example.segundoapiappfixa.adapters.dto.input.ModeloEquipamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModeloEquipamentoCadastrarInputDTO (
        @NotNull(message = "{validation.modeloEquipamento.required}")
        Long id,

        @NotNull(message = "{validation.marcaEquipamento.required}")
        Long marcaEquipamentoId,

        @NotNull(message = "{validation.categoriaEquipamento.required}")
        Long categoriaEquipamentoId,

        @NotBlank(message = "{validation.nome.required}")
        String nome,

        @NotBlank(message = "{validation.descricao.required}")
        String descricao
) {
}
