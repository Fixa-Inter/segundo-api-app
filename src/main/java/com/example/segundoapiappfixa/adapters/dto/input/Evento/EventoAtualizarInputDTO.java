package com.example.segundoapiappfixa.adapters.dto.input.Evento;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

public record EventoAtualizarInputDTO (
        @NotNull(message = "{validation.evento.required}")
        Long id,

        Long localEnderecoId,
        String titulo,
        String descricao,
        String descricaoLocal,
        String observacao,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim
) {
}
