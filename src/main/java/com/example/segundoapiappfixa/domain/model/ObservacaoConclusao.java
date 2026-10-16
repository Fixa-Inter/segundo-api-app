package com.example.segundoapiappfixa.domain.model;

import com.example.segundoapiappfixa.domain.enums.Dificuldade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ObservacaoConclusao {
    private Long id;
    private OrdemServico ordemServico;
    private Dificuldade dificuldade;
    private String observacao;
    private LocalDateTime dataCriacao;
    private List<Foto> fotos;
}
