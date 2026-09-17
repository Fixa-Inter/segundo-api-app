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
public class Turno {
    private Long id;
    private Endereco endereco;
    private String nome;
    private Integer horaInicio;
    private Integer horaFim;
    private Boolean atravessaMeiaNoite;
    private LocalDateTime dataCriacao;
    private Boolean estaAtivo;
}
