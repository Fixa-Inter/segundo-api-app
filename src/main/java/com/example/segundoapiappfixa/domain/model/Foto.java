package com.example.segundoapiappfixa.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Foto {
    private Long id;
    private Problema problema;
    private Ocorrencia ocorrencia;
    private OrdemServico ordemServico;
    private Usuario usuario;
    private String url;
    private Boolean estaAtivo;
}
