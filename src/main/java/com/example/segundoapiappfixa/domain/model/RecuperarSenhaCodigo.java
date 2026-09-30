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
    private Usuario usuario;
    private Integer codigo;
    private Date dataExpiracao;
    private Boolean estaAtivo;
}
