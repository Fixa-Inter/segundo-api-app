package com.example.segundoapiappfixa.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "MarcaEquipamento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarcaEquipamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "esta_ativo")
    private Boolean estaAtivo;
}
