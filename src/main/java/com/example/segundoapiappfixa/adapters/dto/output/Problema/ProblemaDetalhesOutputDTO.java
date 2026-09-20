package com.example.segundoapiappfixa.adapters.dto.output.Problema;

import java.util.List;

public record ProblemaDetalhesOutputDTO(
        String titulo,
        String descricaoProblema,
        String local,
        String descricaoLocal,
        String equipamento,
        String status,
        List<String> urlFoto
) {
}
