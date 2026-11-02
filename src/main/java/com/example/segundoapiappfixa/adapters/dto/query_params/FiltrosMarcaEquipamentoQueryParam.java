package com.example.segundoapiappfixa.adapters.dto.query_params;

import com.example.segundoapiappfixa.domain.filter.FiltrosMarcaEquipamento;

import org.springframework.web.bind.annotation.BindParam;

public record FiltrosMarcaEquipamentoQueryParam(
        String nome,
        String descricao,
        Boolean estaAtivo,
        Integer limite,
        @BindParam("campo_ordenacao") String campoOrdenacao,
        @BindParam("direcao_ordenacao") Integer direcaoOrdenacao
) {
    public FiltrosMarcaEquipamento toDomain() {
        return new FiltrosMarcaEquipamento(nome, descricao, estaAtivo, limite, campoOrdenacao, direcaoOrdenacao);
    }
}
