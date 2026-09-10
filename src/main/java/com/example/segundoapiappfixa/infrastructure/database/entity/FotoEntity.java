package com.example.segundoapiappfixa.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Foto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "problema_id")
    private ProblemaEntity problemaId;

    @ManyToOne
    @JoinColumn(name = "ocorrencia_id")
    private OcorrenciaEntity ocorrenciaId;

    @ManyToOne
    @JoinColumn(name = "ordem_servico_id")
    private OrdemServicoEntity ordemServicoId;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity UsuarioId;

    @Column(name = "url")
    private String url;

    @Column(name = "esta_ativo")
    private Boolean estaAtivo;
}
