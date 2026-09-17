package com.example.segundoapiappfixa.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarefaStatusHistorico {
    private Long id;
    private Tarefa tarefa;
    private StatusOrdemServico statusOrdemServico;
    private LocalDateTime dataAtualizacao;
}
