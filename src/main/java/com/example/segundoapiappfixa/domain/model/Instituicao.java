package com.example.segundoapiappfixa.domain.model;

import com.example.segundoapiappfixa.domain.enums.TipoInstituicao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Instituicao {
    private Long id;
    private String nome;
    private String cnpj;
    private TipoInstituicao tipoInstituicao;
    private String dominioEmail;
    private LocalDate dataCriacao;
    private Boolean estaAtivo;
}
