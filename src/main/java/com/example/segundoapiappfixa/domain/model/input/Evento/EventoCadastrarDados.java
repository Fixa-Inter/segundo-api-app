package com.example.segundoapiappfixa.domain.model.input.Evento;

import java.time.LocalDateTime;

public record EventoCadastrarDados(
        Long usuarioId,
        Long localUnidadeId,
        String titulo,
        String descricao,
        String descricaoLocal,
        String observacao,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim
) {}
