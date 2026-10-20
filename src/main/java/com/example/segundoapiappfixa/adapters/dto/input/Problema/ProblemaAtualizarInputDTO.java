package com.example.segundoapiappfixa.adapters.dto.input.Problema;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ProblemaAtualizarInputDTO(
        @NotNull(message = "{validation.problema.required}") Long problemaId,
        String titulo,
        String descricaoProblema,
        Long localUnidadeId,
        String descricaoLocal,
        Long categoriaEquipamentoId,
        List<String> fotosAdicionar,
        List<Long> fotosExcluir
) { }
