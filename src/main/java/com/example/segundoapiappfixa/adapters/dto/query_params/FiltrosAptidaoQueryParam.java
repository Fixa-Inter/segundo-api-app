package com.example.segundoapiappfixa.adapters.dto.query_params;

import java.math.BigDecimal;



import org.springframework.web.bind.annotation.BindParam;

public record FiltrosAptidaoQueryParam(
        String categoriaProblema,
        BigDecimal nota,
        Boolean estaAtivo,
        Integer limite,
        @BindParam("campo_ordenacao") String campoOrdenacao,
        @BindParam("direcao_ordenacao") Integer direcaoOrdenacao
) {
}
