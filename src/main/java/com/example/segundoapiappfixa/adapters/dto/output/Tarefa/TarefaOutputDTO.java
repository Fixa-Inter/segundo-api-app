package com.example.segundoapiappfixa.adapters.dto.output.Tarefa;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TarefaOutputDTO(
        Long id,
        String tituloTarefa,
        String tituloOrdemServico,
        String usuarioResponsavel,
        LocalDate dataCriacao
) {
}
