package com.example.segundoapiappfixa.domain.filter;



public record FiltrosObservacaoConclusao(
        Long id,
        String titulo,
        String descricaoProblema,
        String categoriaProblema,
        String categoriaEquipamento,
        String localUnidade,
        String descricaoLocal,
        String statusProblema,
        String prioridade,
        String nomeResponsavel,
        String dificuldade,
        String observacao,
        String dataCriacao
) {}
