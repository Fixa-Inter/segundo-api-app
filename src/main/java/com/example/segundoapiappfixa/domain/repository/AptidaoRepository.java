package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Aptidao;
import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AptidaoRepository {
    List<Aptidao> findByUsuarioId(Long usuarioId);

    Map<Long, Aptidao> findCompetenciasVencedorasByUsuarioIds(List<Long> usuarioIds);

    Map<Long, Aptidao> findCompetenciasVencedorasByUsuarioIds(
            List<Long> usuarioIds,
            CategoriaProblema categoriaProblema
    );

    Optional<Aptidao> findById(Long id);

    Aptidao save(Aptidao aptidao);
}
