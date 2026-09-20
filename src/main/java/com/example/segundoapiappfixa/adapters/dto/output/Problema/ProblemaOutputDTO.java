package com.example.segundoapiappfixa.adapters.dto.output.Problema;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProblemaOutputDTO (
        Long id,
        String titulo,
        LocalDate dataCriacao,
        String nomeUsuario
) {
}
