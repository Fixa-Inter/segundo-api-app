package com.example.segundoapiappfixa.adapters.dto.query_params;

import org.springframework.web.bind.annotation.BindParam;

public record FiltrosObservacaoConclusaoQueryParam(
        Long id,
        String titulo,

        @BindParam("descricao_problema")
        String descricaoProblema,

        @BindParam("categoria_problema")
        String categoriaProblema,

        @BindParam("categoria_equipamento")
        String categoriaEquipamento,

        @BindParam("local_endereco")
        String localEndereco,

        @BindParam("descricao_local")
        String descricaoLocal,

        @BindParam("status_problema")
        String statusProblema,

        String prioridade,

        @BindParam("status_ordem_servico")
        String statusOrdemServico,

        @BindParam("nome_responsavel")
        String nomeResponsavel,

        String dificuldade,
        String observacao,

        @BindParam("data_criacao")
        String dataCriacao
) {
}
