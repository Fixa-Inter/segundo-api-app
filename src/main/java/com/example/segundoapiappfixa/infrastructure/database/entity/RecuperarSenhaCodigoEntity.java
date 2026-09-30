package com.example.segundoapiappfixa.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity(name = "recuperar_senha_codigo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecuperarSenhaCodigoEntity {

    private static final int DURACAO = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, name = "usuario_id")
    public UsuarioEntity usuario;

    @Column(name = "codigo")
    private Integer codigo;

    @Column(name = "data_expiracao")
    private Date dataExpiracao;

    @Column(name = "estaAtivo")
    private Boolean estaAtivo;
}
