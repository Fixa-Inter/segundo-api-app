package com.example.segundoapiappfixa.adapters.dto.input.Problema;

import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import jakarta.validation.constraints.NotNull;

public record ProblemaAtualizarStatusDTO(
        @NotNull(message = "{validation.problema.required}")
        Long problemaId,

        @NotNull(message = "{validation.status.required}")
        StatusProblema statusProblema,

        String motivoRecusa
) {
}
