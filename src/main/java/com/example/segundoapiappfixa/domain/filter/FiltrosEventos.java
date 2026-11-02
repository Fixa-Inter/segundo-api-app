package com.example.segundoapiappfixa.domain.filter;

import java.time.LocalDateTime;

public record FiltrosEventos(
        String titulo,
        String descricao,
        String localUnidade,
        String descricaoLocal,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim,
        String observacao,
        String usuario,
        Integer limite,
        String campoOrdenacao,
        Integer direcaoOrdenacao
) {}
