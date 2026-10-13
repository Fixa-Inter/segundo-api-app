package com.example.segundoapiappfixa.adapters.dto.input.OrdemServico;

import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.Prioridade;
import com.example.segundoapiappfixa.domain.enums.StatusOrdemServico;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record OrdemServicoAtualizarInputDTO(

        @NotNull(message = "{validation.ordemServico.id.required}")
        Long id,

        Long usuarioId,
        StatusOrdemServico statusOrdemServico,
        CategoriaProblema categoriaProblema,
        LocalDate dataPrevista,
        Prioridade prioridade
) {
}
