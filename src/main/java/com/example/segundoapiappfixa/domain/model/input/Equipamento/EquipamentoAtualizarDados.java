package com.example.segundoapiappfixa.domain.model.input.Equipamento;



public record EquipamentoAtualizarDados(
        Long id,
        Long modeloEquipamentoId,
        Long localUnidadeId,
        String codigo
) {}
