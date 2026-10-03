package com.example.segundoapiappfixa.adapters.dto.input.OrdemServico;

import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.Prioridade;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record OrdemServicoCadastrarInputDTO(

        @NotNull(message = "{validation.ordemServico.problema.required}")
        Long problemaId,

        @NotNull(message = "{validation.ordemServico.usuario.required}")
        Long usuarioId,

        @NotNull(message = "{validation.ordemServico.statusOrdemServico.required}")
        Long statusOrdemServicoId,

        @NotNull(message = "{validation.ordemServico.categoria.required}")
        CategoriaProblema categoriaProblema,

        @NotNull(message = "{validation.ordemServico.dataPrevista.required}")
        LocalDate dataPrevista,

        @NotNull(message = "{validation.ordemServico.prioridade.required}")
        Prioridade prioridade
) {
}
