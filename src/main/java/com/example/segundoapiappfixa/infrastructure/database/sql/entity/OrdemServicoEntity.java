package com.example.segundoapiappfixa.infrastructure.database.sql.entity;

import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.Prioridade;
import com.example.segundoapiappfixa.domain.enums.StatusOrdemServico;
import com.example.segundoapiappfixa.infrastructure.database.sql.converter.CategoriaProblemaConverter;
import com.example.segundoapiappfixa.infrastructure.database.sql.converter.PrioridadeConverter;
import com.example.segundoapiappfixa.infrastructure.database.sql.converter.StatusOrdemServicoConverter;
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

    @Convert(converter = StatusOrdemServicoConverter.class)
    @Column(name = "status_ordem_servico")
    public StatusOrdemServico statusOrdemServico;

    @Convert(converter = CategoriaProblemaConverter.class)
    @Column(name = "categoria_problema")
    public CategoriaProblema categoriaProblema;

    @Convert(converter = PrioridadeConverter.class)
    @Column(name = "prioridade")
    public Prioridade prioridade;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_prevista")
    private LocalDateTime dataPrevista;
}
