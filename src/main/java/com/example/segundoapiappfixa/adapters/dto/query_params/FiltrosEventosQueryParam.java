package com.example.segundoapiappfixa.adapters.dto.query_params;

import org.springframework.web.bind.annotation.BindParam;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record FiltrosEventosQueryParam(
        String titulo,
        String descricao,

        @BindParam("local_endereco")
        String localEndereco,

        @BindParam("descricao_local")
        String descricaoLocal,

        @BindParam("data_hora_inicio")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime dataHoraInicio,

        @BindParam("data_hora_fim")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime dataHoraFim,

        String observacao,
        String usuario,
        Integer limite,

        @BindParam("campo_ordenacao")
        String campoOrdenacao,

        @BindParam("direcao_ordenacao")
        Integer direcaoOrdenacao
) {
}
