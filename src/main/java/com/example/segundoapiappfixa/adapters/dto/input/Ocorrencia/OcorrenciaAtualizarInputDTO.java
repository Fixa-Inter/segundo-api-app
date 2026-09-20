package com.example.segundoapiappfixa.adapters.dto.input.Ocorrencia;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OcorrenciaAtualizarInputDTO(
        @NotNull(message = "validation.ocorrencia.required")
        @Positive(message = "validation.ocorrencia.invalid")
        Long id,
        Long localEnderecoId,
        Long equipamentoId,
        Long categoriaProblemaId,
        String titulo,
        String descricaoOcorrencia,
        String descricaoLocal,
        Long prioridade
) {
}
