package com.example.segundoapiappfixa.adapters.dto.output.OrdemServico;

import com.example.segundoapiappfixa.adapters.dto.output.Problema.ProblemaDetalhesOutputDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrdemServicoDetalhesOutputDTO(
        ProblemaDetalhesOutputDTO problemaDetalhesOutputDTO,
        String categoriaProblema,
        String prioridade,
        String statusOrdemServico,
        LocalDate dataPrevista,
        Integer quantidadeTarefas
) {
}
