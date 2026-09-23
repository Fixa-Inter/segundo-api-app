package com.example.segundoapiappfixa.adapters.dto.output.Aptidao;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AptidaoOutputDTO (
        Long id,
        String categoriaProblema,
        Double nota,
        LocalDate dataCriacao,
        Boolean estaAtivo
) {
}
