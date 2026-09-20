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
public class Ocorrencia {
    private Long id;
    private Usuario usuario;
    private LocalEndereco localEndereco;
    private Equipamento equipamento;
    private CategoriaProblema categoriaProblema;
    private Prioridade prioridade;
    private String titulo;
    private String descricaoOcorrencia;
    private String descricaoLocal;
    private LocalDateTime dataCriacao;
    private Boolean estaAtivo;
}
