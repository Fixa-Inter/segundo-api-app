package com.example.segundoapiappfixa.domain.filter;



public record FiltrosEquipamento(
        String codigo,
        String localUnidade,
        String usuario,
        Integer limite,
        String campoOrdenacao,
        Integer direcaoOrdenacao
) {}
