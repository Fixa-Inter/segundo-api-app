package com.example.segundoapiappfixa.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "Turno")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TurnoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "local_endereco_id")
    public LocalEnderecoEntity localEndereco;

    @Column(name = "nome")
    private String nome;

    @Column(name = "hora_inicio")
    private Integer horaInicio;

    @Column(name = "hora_fim")
    private Integer horaFim;

    @Column(name = "atravessa_meia_noite")
    private Boolean atravessaMeiaNoite;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "esta_ativo")
    private Boolean estaAtivo;
}
