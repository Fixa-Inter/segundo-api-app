package com.example.segundoapiappfixa.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "TarefaStatusHistorico")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarefaStatusHistoricoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tarefa_id")
    public TarefaEntity tarefa;

    @ManyToOne
    @JoinColumn(name = "status_ordem_servico_id")
    private StatusOrdemServicoEntity statusOrdemServico;

    @Column(name = "data_atualizacao")
    private LocalDateTime data_atualizacao;
}
