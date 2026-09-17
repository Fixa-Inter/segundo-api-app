package com.example.segundoapiappfixa.adapters.dto.output.Problema;

import java.time.LocalDate;

public record ProblemaOutputDTO (
        Long id,
        String titulo,
        LocalDate dataCriacao,
        String nomeUsuario
) {
}
