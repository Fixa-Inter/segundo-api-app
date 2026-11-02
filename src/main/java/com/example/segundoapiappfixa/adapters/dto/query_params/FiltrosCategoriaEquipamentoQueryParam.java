package com.example.segundoapiappfixa.adapters.dto.query_params;

import com.example.segundoapiappfixa.domain.filter.FiltrosCategoriaEquipamento;

import org.springframework.web.bind.annotation.BindParam;

import java.time.LocalDate;

public record FiltrosCategoriaEquipamentoQueryParam(
        String nome,
        String descricao,

        @BindParam("nome_usuario")
        String nomeUsuario,

        Integer limite,

        @BindParam("campo_ordenacao")
        String campoOrdenacao,

        @BindParam("direcao_ordenacao")
        Integer direcaoOrdenacao
) {
    public FiltrosCategoriaEquipamento toDomain() {
        return new FiltrosCategoriaEquipamento(nome, descricao, nomeUsuario, limite, campoOrdenacao, direcaoOrdenacao);
    }
}
