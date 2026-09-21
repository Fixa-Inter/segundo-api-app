package com.example.segundoapiappfixa.adapters.dto.output.ModeloEquipamento;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ModeloEquipamentoOutputDTO(
        Long id,
        String nome,
        String descricao,
        String marca,
        String categoria,
        LocalDate dataCriacao,
        Boolean estaAtivo
) {
}
