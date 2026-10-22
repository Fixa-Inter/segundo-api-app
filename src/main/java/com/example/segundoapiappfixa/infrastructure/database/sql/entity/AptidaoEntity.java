package com.example.segundoapiappfixa.infrastructure.database.sql.entity;

import java.math.BigDecimal;

import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.infrastructure.database.sql.converter.CategoriaProblemaConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "Aptidao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AptidaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    public UsuarioEntity usuario;

    @Convert(converter = CategoriaProblemaConverter.class)
    @Column(name = "categoria_problema")
    private CategoriaProblema categoriaProblema;

    @Column(name = "nota")
    private BigDecimal nota;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "esta_ativo")
    private Boolean estaAtivo;
}
