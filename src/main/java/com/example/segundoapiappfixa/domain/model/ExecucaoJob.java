package com.example.segundoapiappfixa.domain.model;

import com.example.segundoapiappfixa.domain.enums.StatusExecucaoJob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExecucaoJob {
    private String id;
    private String nome;
    private LocalDate dataExecucao;
    private LocalDateTime iniciadoEm;
    private LocalDateTime finalizadoEm;
    private StatusExecucaoJob status;
    private Integer registrosProcessados;
    private String erro;
}
