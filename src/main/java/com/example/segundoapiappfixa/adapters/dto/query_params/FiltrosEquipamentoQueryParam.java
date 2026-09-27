package com.example.segundoapiappfixa.adapters.dto.query_params;

import org.springframework.web.bind.annotation.BindParam;

public record FiltrosEquipamentoQueryParam(
        String codigo,

        @BindParam("local_endereco")
        String localEndereco,

        String usuario,
        Integer limite,

        @BindParam("campo_ordenacao")
        String campoOrdenacao,

        @BindParam("direcao_ordenacao")
        Integer direcaoOrdenacao
) {
}
