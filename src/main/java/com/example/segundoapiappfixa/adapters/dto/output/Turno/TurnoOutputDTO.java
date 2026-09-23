package com.example.segundoapiappfixa.adapters.dto.output.Turno;

import java.time.LocalDate;

public record TurnoOutputDTO(
        Long id,
        String nome,
        Integer horaInicio,
        Integer horaFim,
        Boolean atravessaMeiaNoite,
        LocalDate dataCriacao,
        Boolean estaAtivo
) {
}
