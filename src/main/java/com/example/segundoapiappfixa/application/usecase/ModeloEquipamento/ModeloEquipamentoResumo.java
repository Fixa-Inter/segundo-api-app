package com.example.segundoapiappfixa.application.usecase.ModeloEquipamento;

import com.example.segundoapiappfixa.domain.model.ModeloEquipamento;

public record ModeloEquipamentoResumo(
        ModeloEquipamento modeloEquipamento,
        Long quantidadeAparelhos
) {
}
