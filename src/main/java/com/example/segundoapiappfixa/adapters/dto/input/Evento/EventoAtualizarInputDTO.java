package com.example.segundoapiappfixa.adapters.dto.input.Evento;

import com.example.segundoapiappfixa.domain.model.input.Evento.EventoAtualizarDados;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

public record EventoAtualizarInputDTO (
        @NotNull(message = "{validation.evento.required}")
        Long id,

        Long localUnidadeId,
        String titulo,
        String descricao,
        String descricaoLocal,
        String observacao,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim
) {
    public EventoAtualizarDados toDomain() {
        return new EventoAtualizarDados(id, localUnidadeId, titulo, descricao, descricaoLocal, observacao, dataHoraInicio, dataHoraFim);
    }
}
