package com.example.segundoapiappfixa.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "Evento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventoEntity {

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

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "descricao_local")
    private String descricaoLocal;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "data_hora_inicio")
    private LocalDateTime dataHoraInicio;

    @Column(name = "data_hora_fim")
    private LocalDateTime dataHoraFim;
}
