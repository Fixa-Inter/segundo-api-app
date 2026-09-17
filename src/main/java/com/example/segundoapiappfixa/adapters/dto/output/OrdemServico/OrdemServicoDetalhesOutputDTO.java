package com.example.segundoapiappfixa.adapters.dto.output.OrdemServico;

import com.example.segundoapiappfixa.adapters.dto.output.Problema.ProblemaDetalhesOutputDTO;

import java.time.LocalDate;

public record OrdemServicoDetalhesOutputDTO(
        ProblemaDetalhesOutputDTO problemaDetalhesOutputDTO,
        String categoriaProblema,
        String prioridade,
        String statusOrdemServico,
        LocalDate dataPrevista,
        Integer quantidadeTarefas
) {
}
