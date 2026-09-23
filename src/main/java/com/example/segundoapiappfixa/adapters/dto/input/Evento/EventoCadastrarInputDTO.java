package com.example.segundoapiappfixa.adapters.dto.input.Evento;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EventoCadastrarInputDTO (
        @NotNull(message = "{validation.usuario.required}")
        Long usuarioId,
        @NotNull(message = "{validation.local.required}")
        Long localEnderecoId,
        @NotBlank(message = "{validation.titulo.required}")
        String titulo,
        @NotBlank(message = "{validation.descricao.required}")
        String descricao,
        @NotBlank(message = "{validation.descricaoLocal.required}")
        String descricaoLocal,
        String observacao,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim
) {
}
