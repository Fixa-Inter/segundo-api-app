package com.example.segundoapiappfixa.adapters.dto.query_params;

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
}
