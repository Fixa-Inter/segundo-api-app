package com.example.segundoapiappfixa.domain.model;

import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.Prioridade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdemServico {
    private Long id;
    private Problema problema;
    private Usuario usuario;
    private StatusOrdemServico statusOrdemServico;
    private CategoriaProblema categoriaProblema;
    private Prioridade prioridade;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataPrevisao;
}
