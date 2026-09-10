package com.example.segundoapiappfixa.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Equipamento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EquipamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    public UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "modelo_equipamento_id")
    public ModeloEquipamentoEntity modeloEquipamento;

    @ManyToOne
    @JoinColumn(name = "local_endereco_id")
    public LocalEnderecoEntity localEndereco;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "esta_ativo")
    private Boolean estaAtivo;
}
