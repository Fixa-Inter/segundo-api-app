package com.example.segundoapiappfixa.adapters.dto.output.Evento;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record EventoOutputDTO(
        Long id,
        String titulo,
        String descricao,
        String localEndereco,
        String descricaoLocal,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim,
        String observacao,
        String usuario,
        LocalDate dataCriacao
) {
}
