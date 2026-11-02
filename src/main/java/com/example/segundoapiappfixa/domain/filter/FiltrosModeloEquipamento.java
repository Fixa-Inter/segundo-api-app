package com.example.segundoapiappfixa.domain.filter;



public record FiltrosModeloEquipamento(
        String nome,
        String descricao,
        String marca,
        String categoria,
        Integer limite,
        String campoOrdenacao,
        Integer direcaoOrdenacao
) {}
