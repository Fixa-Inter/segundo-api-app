package com.example.segundoapiappfixa.domain.model;

import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Aptidao {
    private Long id;
    private Usuario usuario;
    private CategoriaProblema categoriaProblema;
    private Double nota;
    private LocalDateTime dataCriacao;
    private Boolean estaAtivo;
}
