package com.example.segundoapiappfixa.adapters.dto.input.Equipamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EquipamentoCadastrarInputDTO(
        @NotNull(message = "{validation.usuario.required}")
        Long usuarioId,

        @NotNull(message = "{validation.modeloEquipamento.required}")
        Long modeloEquipamentoId,

        @NotNull(message = "{validation.local.required}")
        Long localEnderecoId,

        @NotBlank(message = "{validation.codigo.required}")
        String codigo
) {
}
