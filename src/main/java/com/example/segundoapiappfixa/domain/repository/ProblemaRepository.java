package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.domain.filter.FiltrosProblema;


import java.util.List;
import java.util.Optional;

public interface ProblemaRepository {
    List<Problema> findAllByUsuarioId(
            Long usuarioId,
            FiltrosProblema filtros
    );

    List<Problema> findAllByLocalUnidadeIds(
            List<Long> localUnidadeIds,
            FiltrosProblema filtros
    );

    Optional<Problema> findById(Long problemaId);

    Long save(Problema problema, List<String> fotos);

    Problema update(Problema problema);

    void deleteById(Long problemaId);

}
