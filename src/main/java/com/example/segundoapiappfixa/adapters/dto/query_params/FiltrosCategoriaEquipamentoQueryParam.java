package com.example.segundoapiappfixa.adapters.dto.query_params;

import java.time.LocalDate;

public record FiltrosCategoriaEquipamentoQueryParam(
        String nome,
        String descricao,
        String nomeUsuario,
        Integer limite,
        Integer direcaoOrdenacao
) {
}
