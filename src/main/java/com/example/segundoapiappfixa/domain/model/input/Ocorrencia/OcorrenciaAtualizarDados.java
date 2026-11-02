package com.example.segundoapiappfixa.domain.model.input.Ocorrencia;

import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.Prioridade;
import java.util.List;

public record OcorrenciaAtualizarDados(
        Long id,
        Long localUnidadeId,
        Long categoriaEquipamentoId,
        CategoriaProblema categoriaProblemaId,
        String titulo,
        String descricaoOcorrencia,
        String descricaoLocal,
        Prioridade prioridade,
        List<Long> fotosExcluir,
        List< String> fotosAdicionar
) {}
