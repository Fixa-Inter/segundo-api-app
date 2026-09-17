package com.example.segundoapiappfixa.domain.model;

import com.example.segundoapiappfixa.domain.enums.TipoInstituicao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Instituicao {
    private Long id;
    private String nome;
    private TipoInstituicao tipoInstituicao;
    private String dominioEmail;
    private LocalDateTime dataCriacao;
    private Boolean estaAtivo;
}
