package com.example.segundoapiappfixa.infrastructure.database.sql.entity;

import com.example.segundoapiappfixa.domain.enums.TipoLocalUnidade;
import com.example.segundoapiappfixa.infrastructure.database.sql.converter.TipoLocalUnidadeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "LocalUnidade")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocalUnidadeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "unidade_id")
    private UnidadeEntity unidade;

    @Column(name = "nome")
    private String nome;

    @Convert(converter = TipoLocalUnidadeConverter.class)
    @Column(name = "tipo_local_unidade")
    private TipoLocalUnidade tipoLocalUnidade;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "esta_ativo")
    private Boolean estaAtivo;
}
