package com.example.segundoapiappfixa.infrastructure.database.sql.entity;

import com.example.segundoapiappfixa.domain.enums.StatusOrdemServico;
import com.example.segundoapiappfixa.infrastructure.database.sql.converter.StatusOrdemServicoConverter;
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

    @Convert(converter = StatusOrdemServicoConverter.class)
    @Column(name = "status_ordem_servico")
    public StatusOrdemServico statusOrdemServico;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
}
