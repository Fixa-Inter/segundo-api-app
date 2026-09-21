package com.example.segundoapiappfixa.adapters.dto.output.MarcaEquipamento;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MarcaEquipamentoOutputDTO(
        Long id,
        String nome,
        String descricao,
        LocalDate dataCriacao,
        Boolean estaAtivo
) {
}
