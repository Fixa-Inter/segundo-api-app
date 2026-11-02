package com.example.segundoapiappfixa.domain.model.input.Problema;

import java.util.List;

public record ProblemaAtualizarDados(
        Long problemaId,
        String titulo,
        String descricaoProblema,
        Long localUnidadeId,
        String descricaoLocal,
        Long categoriaEquipamentoId,
        List<String> fotosAdicionar,
        List<Long> fotosExcluir
) {}
