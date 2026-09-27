package com.example.segundoapiappfixa.adapters.dto.query_params;

import org.springframework.web.bind.annotation.BindParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record FiltrosOrdemServicoQueryParam(
        String titulo,
        String descricao,

        @BindParam("categoria_problema")
        String categoriaProblema,

        @BindParam("categoria_equipamento")
        String categoriaEquipamento,

        String prioridade,

        @BindParam("data_prevista_min")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime dataPrevistaMin,

        @BindParam("data_prevista_max")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime dataPrevistaMax,

        @BindParam("nome_usuario")
        String nomeUsuario,

        @BindParam("tipo_acesso")
        String tipoAcesso,

        Integer limite,

        @BindParam("campo_ordenacao")
        String campoOrdenacao,

        @BindParam("direcao_ordenacao")
        Integer direcaoOrdenacao
) {
}
