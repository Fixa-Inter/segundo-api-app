package com.example.segundoapiappfixa.adapters.dto.input.Tarefa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TarefaCriarInputDTO(
        @NotNull(message = "{validation.tarefa.ordemServico.required}")
        Long ordemServicoId,

        @NotNull(message = "{validation.tarefa.status.required}")
        Long statusOrdemServicoId,

        @NotBlank(message = "{validation.tarefa.titulo.required}")
        String titulo,

        @NotBlank(message = "{validation.tarefa.descricao.required}")
        String descricao
) {
}
