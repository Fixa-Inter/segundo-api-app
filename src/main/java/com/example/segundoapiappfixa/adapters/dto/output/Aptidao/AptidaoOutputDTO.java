package com.example.segundoapiappfixa.adapters.dto.output.Aptidao;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AptidaoOutputDTO (
        Long id,
        String categoriaProblema,
        BigDecimal nota,
        LocalDate dataCriacao,
        Boolean estaAtivo
) {
}
