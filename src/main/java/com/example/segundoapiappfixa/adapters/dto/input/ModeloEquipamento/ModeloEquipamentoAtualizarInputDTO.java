package com.example.segundoapiappfixa.adapters.dto.input.ModeloEquipamento;

import jakarta.validation.constraints.NotBlank;
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
}
