package com.example.segundoapiappfixa.adapters.dto.input.Tarefa;

import jakarta.validation.constraints.NotNull;

public record TarefaAtualizarInputDTO(
        @NotNull(message = "{validation.tarefa.required}")
        Long id,

        String titulo,
        String descricao
) {
}
