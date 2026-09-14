package com.example.segundoapiappfixa.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Equipamento {
    private Long id;
    private Usuario usuario;
    private ModeloEquipamento modeloEquipamento;
    private LocalEndereco localEndereco;
    private String codigo;
    private Boolean estaAtivo;
}
