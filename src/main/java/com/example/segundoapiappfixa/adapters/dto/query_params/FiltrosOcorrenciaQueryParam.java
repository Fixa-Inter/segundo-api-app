package com.example.segundoapiappfixa.adapters.dto.query_params;

import org.springframework.web.bind.annotation.BindParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

public record FiltrosOcorrenciaQueryParam(
        String titulo,

        @BindParam("descricao_ocorrencia")
        String descricaoOcorrencia,

        @BindParam("categoria_problema")
        String categoriaProblema,

        String prioridade,

        @BindParam("local_endereco")
        String localEndereco,

        @BindParam("descricao_local")
        String descricaoLocal,

        @BindParam("equipamento_codigo")
        String equipamentoCodigo,

        @BindParam("nome_usuario")
        String nomeUsuario,

        @BindParam("tipo_acesso")
        String tipoAcesso,

        @BindParam("data_criacao_min")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime dataCriacaoMin,

        @BindParam("data_criacao_max")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime dataCriacaoMax,

        Integer limite,

        @BindParam("campo_ordenacao")
        String campoOrdenacao,

        @BindParam("direcao_ordenacao")
        Integer direcaoOrdenacao
) {
}
