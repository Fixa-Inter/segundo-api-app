package com.example.segundoapiappfixa.infrastructure.database.sql.entity;

import com.example.segundoapiappfixa.domain.enums.Dificuldade;
import com.example.segundoapiappfixa.infrastructure.database.sql.converter.DificuldadeConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "ObservacaoConclusao")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ObservacaoConclusaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ordem_servico_id", nullable = false, unique = true)
    private OrdemServicoEntity ordemServico;

    @Convert(converter = DificuldadeConverter.class)
    @Column(name = "dificuldade", nullable = false)
    private Dificuldade dificuldade;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @OneToMany(mappedBy = "observacaoConclusao")
    private List<FotoEntity> fotos = new ArrayList<>();
}
