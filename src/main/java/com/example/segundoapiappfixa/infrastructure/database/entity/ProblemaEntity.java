package com.example.segundoapiappfixa.infrastructure.database.entity;

import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "Problema")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProblemaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    public UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "categoria_equipamento_id")
    public CategoriaEquipamentoEntity categoriaEquipamento;

    @ManyToOne
    @JoinColumn(name = "local_endereco_id")
    public LocalEnderecoEntity localEndereco;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descricao_problema")
    private String descricaoProblema;

    @Column(name = "descricao_local")
    private String descricaoLocal;

    @Column(name = "motivo_recusa")
    private String motivoRecusa;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "status")
    private StatusProblema status;
}
