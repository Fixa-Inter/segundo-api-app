package com.example.segundoapiappfixa.adapters.dto.input.OrdemServico;

import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.Prioridade;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record OrdemServicoAtualizarInputDTO(

        @NotNull(message = "{validation.ordemServico.id.required}")
        Long id,

        Long usuarioId,
        Long statusOrdemServicoId,
        CategoriaProblema categoriaProblema,
        LocalDate dataPrevista,
        Prioridade prioridade
) {
}
