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
public class TurnoUsuario {
    private Long id;
    private Turno turno;
    private Usuario usuario;
    private LocalDateTime dataCriacao;
    private Boolean estaAtivo;
}
