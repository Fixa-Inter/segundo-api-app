package com.example.segundoapiappfixa.domain.filter;

import java.time.LocalDate;

public record FiltrosCategoriaEquipamento(
        String nome,
        String descricao,
        String nomeUsuario,
        Integer limite,
        String campoOrdenacao,
        Integer direcaoOrdenacao
) {}
