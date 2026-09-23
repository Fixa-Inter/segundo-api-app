package com.example.segundoapiappfixa.adapters.dto.output.Equipamento;

import java.time.LocalDate;

public record EquipamentoOutputDTO(
        Long id,
        String codigo,
        String modeloEquipamento,
        String localEndereco,
        String usuario,
        LocalDate dataCriacao,
        Boolean estaAtivo
) {
}
