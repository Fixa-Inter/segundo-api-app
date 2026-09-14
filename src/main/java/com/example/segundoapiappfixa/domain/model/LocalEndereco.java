package com.example.segundoapiappfixa.domain.model;

import com.example.segundoapiappfixa.domain.enums.TipoLocalEndereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocalEndereco {
    private Long id;
    private Endereco endereco;
    private String nome;
    private TipoLocalEndereco tipoLocalEndereco;
    private String descricao;
    private LocalDateTime dataCriacao;
    private Boolean estaAtivo;
}
