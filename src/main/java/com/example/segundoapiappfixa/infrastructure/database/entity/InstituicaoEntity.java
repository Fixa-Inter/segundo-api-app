package com.example.segundoapiappfixa.infrastructure.database.entity;

import com.example.segundoapiappfixa.domain.enums.TipoInstituicao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "Instituicao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstituicaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "tipo_instituicao")
    private TipoInstituicao tipoInstituicao;

    @Column(name = "dominio_email")
    private String dominioEmail;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Column(name = "esta_ativo")
    private Boolean estaAtivo;
}
