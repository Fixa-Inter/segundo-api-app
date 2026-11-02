package com.example.segundoapiappfixa.domain.model.input.ModeloEquipamento;



public record ModeloEquipamentoAtualizarDados(
        Long id,
        Long marcaEquipamentoId,
        Long categoriaEquipamentoId,
        String nome,
        String descricao,
        Boolean estaAtivo
) {}
