package com.example.segundoapiappfixa.adapters.dto.query_params;

import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import org.springframework.web.bind.annotation.BindParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

public record FiltrosProblemaQueryParam(
        String titulo,

        @BindParam("descricao_problema")
        String descricaoProblema,

        @BindParam("categoria_equipamento")
        String categoriaEquipamento,

        @BindParam("local_unidade")
        String localUnidade,

        @BindParam("descricao_local")
        String descricaoLocal,

        @BindParam("equipamento_codigo")
        String equipamentoCodigo,

        @BindParam("nome_usuario")
        String nomeUsuario,

        @BindParam("tipo_acesso")
        String tipoAcesso,

        @BindParam("status_problema")
        StatusProblema statusProblema,

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
