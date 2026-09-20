package com.example.segundoapiappfixa.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "OrdemServicoStatusHistorico")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdemServicoStatusHistoricoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ordem_servico_id")
    public OrdemServicoEntity ordemServico;

    @ManyToOne
    @JoinColumn(name = "status_ordem_servico_id")
    private StatusOrdemServicoEntity statusOrdemServico;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
}
