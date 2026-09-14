package com.example.segundoapiappfixa.adapters.dto.output;

import java.time.LocalDate;

public record ProblemaOutputDTO (
        String titulo,
        LocalDate dataCriacao,
        String nomeUsuario
) {
}
