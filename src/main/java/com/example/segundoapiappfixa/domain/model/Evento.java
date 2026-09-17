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
public class Evento {
    private Long id;
    private Usuario usuario;
    private LocalEndereco localEndereco;
    private String titulo;
    private String descricao;
    private String descricaoLocal;
    private String observacao;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private LocalDateTime dataCriacao;
}
