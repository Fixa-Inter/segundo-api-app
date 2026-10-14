package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Unidade;

import java.util.List;
import java.util.Optional;

public interface UnidadeRepository {

    List<Unidade> findAllByInstituicaoId(Long instituicaoId);

    Optional<Unidade> findById(Long id);

}
