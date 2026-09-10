package com.example.segundoapiappfixa.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tarefa {
    private Long id;
    private OrdemServico ordemServico;
    private StatusOrdemServico statusOrdemServico;
    private String titulo;
    private String descricao;
}
