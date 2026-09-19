package com.example.segundoapiappfixa.adapters.dto.output.Tarefa;

import java.time.LocalDate;

public record TarefaOutputDTO(
        Long id,
        String tituloTarefa,
        String tituloOrdemServico,
        String usuarioResponsavel,
        LocalDate dataCriacao
) {
}
