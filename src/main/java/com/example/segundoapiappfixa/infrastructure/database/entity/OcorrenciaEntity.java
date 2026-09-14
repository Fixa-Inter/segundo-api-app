package com.example.segundoapiappfixa.infrastructure.database.entity;

import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "Ocorrencia")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OcorrenciaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    public UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "local_endereco_id")
    public LocalEnderecoEntity localEndereco;

    @ManyToOne
    @JoinColumn(name = "equipamento_id")
    public EquipamentoEntity equipamento;

    @Column(name = "categoria_problema")
    public CategoriaProblema categoriaProblema;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descricao_ocorrencia")
    private String descricaoOcorrencia;

    @Column(name = "descricao_local")
    private String descricaoLocal;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "esta_ativo")
    private Boolean estaAtivo;
}
