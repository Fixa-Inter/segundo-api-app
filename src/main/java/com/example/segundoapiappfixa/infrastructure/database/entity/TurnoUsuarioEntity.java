package com.example.segundoapiappfixa.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "TurnoUsuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TurnoUsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "turno_id")
    public TurnoEntity turno;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    public UsuarioEntity usuario;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "esta_ativo")
    private Boolean estaAtivo;
}
