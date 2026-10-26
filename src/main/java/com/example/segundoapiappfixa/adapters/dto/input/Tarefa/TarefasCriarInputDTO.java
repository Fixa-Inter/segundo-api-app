package com.example.segundoapiappfixa.adapters.dto.input.Tarefa;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/** Corpo em lote de criação de tarefas. */
public record TarefasCriarInputDTO(
        @NotEmpty(message = "{validation.tarefa.lista.required}")
        List<@Valid TarefaCriarInputDTO> tarefas
){ }
