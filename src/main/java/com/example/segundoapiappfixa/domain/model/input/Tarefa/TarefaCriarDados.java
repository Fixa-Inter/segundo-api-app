package com.example.segundoapiappfixa.domain.model.input.Tarefa;

import com.example.segundoapiappfixa.domain.enums.StatusOrdemServico;

public record TarefaCriarDados(
        Long ordemServicoId,
        StatusOrdemServico statusOrdemServico,
        String titulo,
        String descricao
) {}
