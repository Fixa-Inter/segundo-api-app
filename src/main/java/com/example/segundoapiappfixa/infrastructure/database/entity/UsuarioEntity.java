package com.example.segundoapiappfixa.infrastructure.database.entity;

import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "Usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private EnderecoEntity endereco;

    @Column(name = "nome_completo")
    private String nomeCompleto;

    @Column(name = "email")
    private String email;

    @Column(name = "tipo_acesso")
    private TipoAcesso tipoAcesso;

    @Column(name = "senha_hash")
    private String senhaHash;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "esta_ativo")
    private Boolean estaAtivo;

    @Column(name = "primeiro_acesso")
    private Boolean primeiroAcesso;
}
