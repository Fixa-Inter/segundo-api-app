package com.example.segundoapiappfixa.adapters.dto.query_params;

import com.example.segundoapiappfixa.domain.filter.FiltrosTarefa;

import org.springframework.web.bind.annotation.BindParam;

public record FiltrosTarefaQueryParam(
        String titulo,
        String descricao,
        String status,

        @BindParam("usuario_responsavel")
        String usuarioResponsavel,

        Integer limite,

        @BindParam("campo_ordenacao")
        String campoOrdenacao,

        @BindParam("direcao_ordenacao")
        Integer direcaoOrdenacao
) {
    public FiltrosTarefa toDomain() {
        return new FiltrosTarefa(titulo, descricao, status, usuarioResponsavel, limite, campoOrdenacao, direcaoOrdenacao);
    }
}
