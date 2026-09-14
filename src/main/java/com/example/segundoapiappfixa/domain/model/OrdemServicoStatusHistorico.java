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
public class OrdemServicoStatusHistorico {
    private Long id;
    private OrdemServico ordemServico;
    private StatusOrdemServico statusOrdemServico;
    private LocalDateTime data_atualizacao;
}
