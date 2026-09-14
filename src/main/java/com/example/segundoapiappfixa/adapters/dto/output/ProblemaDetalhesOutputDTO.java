package com.example.segundoapiappfixa.adapters.dto.output;

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
