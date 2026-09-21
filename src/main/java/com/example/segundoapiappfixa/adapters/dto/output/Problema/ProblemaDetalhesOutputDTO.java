package com.example.segundoapiappfixa.adapters.dto.output.Problema;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
