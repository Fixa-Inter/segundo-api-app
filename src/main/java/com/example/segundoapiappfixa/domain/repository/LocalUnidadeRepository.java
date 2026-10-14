package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.LocalUnidade;

import java.util.List;
import java.util.Optional;

public interface LocalUnidadeRepository {

    List<LocalUnidade> findAllByUnidadeId(Long unidadeId);

    Optional<LocalUnidade> findById(Long id);
}
