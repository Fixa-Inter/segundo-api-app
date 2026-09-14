package com.example.segundoapiappfixa.infrastructure.database.entity;

import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.Prioridade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "OrdemServico")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdemServicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "problema_id")
    public ProblemaEntity problema;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    public UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "status_ordem_servico_id")
    public StatusOrdemServicoEntity statusOrdemServico;

    @Column(name = "categoria_problema")
    public CategoriaProblema categoriaProblema;

    @Column(name = "prioridade")
    public Prioridade prioridade;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_previsao")
    private LocalDateTime dataPrevisao;
}
