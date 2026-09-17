package com.example.segundoapiappfixa.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "Tarefa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarefaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ordem_servico_id")
    public OrdemServicoEntity ordemServico;

    @ManyToOne
    @JoinColumn(name = "status_ordem_servico_id")
    public StatusOrdemServicoEntity statusOrdemServico;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
}
