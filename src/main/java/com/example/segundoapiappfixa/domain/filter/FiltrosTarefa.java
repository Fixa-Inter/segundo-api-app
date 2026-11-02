package com.example.segundoapiappfixa.domain.filter;



public record FiltrosTarefa(
        String titulo,
        String descricao,
        String status,
        String usuarioResponsavel,
        Integer limite,
        String campoOrdenacao,
        Integer direcaoOrdenacao
) {}
