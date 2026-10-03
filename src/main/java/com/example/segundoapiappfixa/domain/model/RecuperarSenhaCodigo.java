package com.example.segundoapiappfixa.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecuperarSenhaCodigo {
    private Long id;
    private Integer codigo;
    private Long usuarioId;
    private Boolean estaAtivo;
}
