package com.example.segundoapiappfixa.domain.model;

import com.example.segundoapiappfixa.domain.enums.TipoLocalUnidade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocalUnidade {
    private Long id;
    private Unidade unidade;
    private String nome;
    private TipoLocalUnidade tipoLocalUnidade;
    private String descricao;
    private LocalDateTime dataCriacao;
    private Boolean estaAtivo;
}
