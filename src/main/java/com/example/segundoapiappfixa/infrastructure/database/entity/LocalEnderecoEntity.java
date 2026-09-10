package com.example.segundoapiappfixa.infrastructure.database.entity;

import com.example.segundoapiappfixa.domain.enums.TipoLocalEndereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "LocalEndereco")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocalEnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private EnderecoEntity endereco;

    @Column(name = "nome")
    private String nome;

    @Column(name = "tipo_local_endereco")
    private TipoLocalEndereco tipoLocalEndereco;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "esta_ativo")
    private Boolean estaAtivo;
}
