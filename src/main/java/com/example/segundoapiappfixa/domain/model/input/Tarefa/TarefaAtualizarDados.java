package com.example.segundoapiappfixa.domain.model.input.Tarefa;

import com.example.segundoapiappfixa.domain.enums.StatusOrdemServico;

public record TarefaAtualizarDados(
        Long id,
        String titulo,
        String descricao,
        StatusOrdemServico statusOrdemServico
) {}
