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
    private Problema problemaId;
    private Ocorrencia ocorrenciaId;
    private OrdemServico ordemServicoId;
    private Usuario UsuarioId;
    private String url;
    private Boolean estaAtivo;
}
