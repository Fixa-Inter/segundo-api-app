package com.example.segundoapiappfixa.domain.model.input.ModeloEquipamento;



public record ModeloEquipamentoCadastrarDados(
        Long id,
        Long marcaEquipamentoId,
        Long categoriaEquipamentoId,
        String nome,
        String descricao
) {}
