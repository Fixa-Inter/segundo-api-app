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
public class ModeloEquipamento {
    private Long id;
    private MarcaEquipamento marcaEquipamento;
    private CategoriaEquipamento categoriaEquipamento;
    private String nome;
    private String descricao;
    private LocalDateTime dataCriacao;
    private Boolean estaAtivo;
}
