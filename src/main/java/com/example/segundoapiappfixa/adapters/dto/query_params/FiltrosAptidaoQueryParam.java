package com.example.segundoapiappfixa.adapters.dto.query_params;

import org.springframework.web.bind.annotation.BindParam;

public record FiltrosAptidaoQueryParam(
        String categoriaProblema,
        Double nota,
        Boolean estaAtivo,
        Integer limite,
        @BindParam("campo_ordenacao") String campoOrdenacao,
        @BindParam("direcao_ordenacao") Integer direcaoOrdenacao
) {
}
