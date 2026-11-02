package com.example.segundoapiappfixa.adapters.dto.query_params;

import com.example.segundoapiappfixa.domain.filter.FiltrosEquipamento;

import org.springframework.web.bind.annotation.BindParam;

public record FiltrosEquipamentoQueryParam(
        String codigo,

        @BindParam("local_unidade")
        String localUnidade,

        String usuario,
        Integer limite,

        @BindParam("campo_ordenacao")
        String campoOrdenacao,

        @BindParam("direcao_ordenacao")
        Integer direcaoOrdenacao
) {
    public FiltrosEquipamento toDomain() {
        return new FiltrosEquipamento(codigo, localUnidade, usuario, limite, campoOrdenacao, direcaoOrdenacao);
    }
}
