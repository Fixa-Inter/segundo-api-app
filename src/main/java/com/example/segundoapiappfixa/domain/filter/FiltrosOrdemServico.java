package com.example.segundoapiappfixa.domain.filter;

import java.time.LocalDateTime;

public record FiltrosOrdemServico(
        String titulo,
        String descricao,
        String categoriaProblema,
        String categoriaEquipamento,
        String prioridade,
        LocalDateTime dataPrevistaMin,
        LocalDateTime dataPrevistaMax,
        String statusOrdemServico,
        String nomeUsuario,
        String tipoAcesso,
        Integer limite,
        String campoOrdenacao,
        Integer direcaoOrdenacao
) {}
