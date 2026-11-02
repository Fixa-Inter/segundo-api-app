package com.example.segundoapiappfixa.domain.filter;



public record FiltrosMarcaEquipamento(
        String nome,
        String descricao,
        Boolean estaAtivo,
        Integer limite,
        String campoOrdenacao,
        Integer direcaoOrdenacao
) {}
