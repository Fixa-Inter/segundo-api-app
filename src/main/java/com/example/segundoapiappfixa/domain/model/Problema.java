package com.example.segundoapiappfixa.domain.model;

import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Problema {
    private Long id;
    private Usuario usuario;
    private CategoriaEquipamento categoriaEquipamento;
    private LocalEndereco localEndereco;
    private String titulo;
    private String descricaoProblema;
    private String descricaoLocal;
    private String motivoRecusa;
    private LocalDateTime dataCriacao;
    private StatusProblema status;
}
