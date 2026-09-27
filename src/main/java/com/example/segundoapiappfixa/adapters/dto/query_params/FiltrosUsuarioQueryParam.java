package com.example.segundoapiappfixa.adapters.dto.query_params;

import org.springframework.web.bind.annotation.BindParam;

public record FiltrosUsuarioQueryParam(
        @BindParam("nome_completo")
        String nomeCompleto,

        String email,
        String cargo,

        @BindParam("tipo_acesso")
        String tipoAcesso,

        @BindParam("cnpj_endereco")
        String cnpjEndereco,

        @BindParam("data_nascimento")
        String dataNascimento,

        Boolean estaAtivo,
        Integer limite,

        @BindParam("campo_ordenacao")
        String campoOrdenacao,

        @BindParam("direcao_ordenacao")
        Integer direcaoOrdenacao
) {
}
