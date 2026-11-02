package com.example.segundoapiappfixa.domain.model.input.Equipamento;



public record EquipamentoCadastrarDados(
        Long usuarioId,
        Long modeloEquipamentoId,
        Long localUnidadeId,
        String codigo
) {}
