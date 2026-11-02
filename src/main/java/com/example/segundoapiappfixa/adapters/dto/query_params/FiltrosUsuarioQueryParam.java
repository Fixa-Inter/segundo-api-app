package com.example.segundoapiappfixa.adapters.dto.query_params;

import com.example.segundoapiappfixa.domain.filter.FiltrosUsuario;

import org.springframework.web.bind.annotation.BindParam;

public record FiltrosUsuarioQueryParam(
        @BindParam("nome_completo")
        String nomeCompleto,

        String email,
        String cargo,

        @BindParam("tipo_acesso")
        String tipoAcesso,

        @BindParam("cnpj_unidade")
        String cnpjUnidade,

        @BindParam("data_nascimento")
        String dataNascimento,

        Boolean estaAtivo,
        Integer limite,

        @BindParam("campo_ordenacao")
        String campoOrdenacao,

        @BindParam("direcao_ordenacao")
        Integer direcaoOrdenacao
) {
    public FiltrosUsuario toDomain() {
        return new FiltrosUsuario(nomeCompleto, email, cargo, tipoAcesso, cnpjUnidade, dataNascimento, estaAtivo, limite, campoOrdenacao, direcaoOrdenacao);
    }
}
