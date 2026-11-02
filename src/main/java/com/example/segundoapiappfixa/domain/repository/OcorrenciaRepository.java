package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Ocorrencia;
import java.util.List;
import com.example.segundoapiappfixa.domain.filter.FiltrosOcorrencia;

import java.util.List;
import java.util.Optional;

public interface OcorrenciaRepository {
    List<Ocorrencia> findAllByUsuarioId(
            Long usuarioId,
            FiltrosOcorrencia filtros
    );

    List<Ocorrencia> findAllByLocalUnidadeIds(
            List<Long> localUnidadeIds,
            FiltrosOcorrencia filtros
    );

    Optional<Ocorrencia> findById(Long ocorrenciaId);

    Long save(Ocorrencia ocorrencia, List<String> fotos);

    Ocorrencia update(Ocorrencia ocorrencia);

    Optional<Ocorrencia> deleteById(Long ocorrenciaId);
}
