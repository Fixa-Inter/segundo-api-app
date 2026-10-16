package com.example.segundoapiappfixa.infrastructure.database.sql.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.InstituicaoMapper;
import com.example.segundoapiappfixa.domain.model.Instituicao;
import com.example.segundoapiappfixa.domain.repository.InstituicaoRepository;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.JpaInstituicaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class InstituicaoRepositoryImpl implements InstituicaoRepository {
    private final JpaInstituicaoRepository repository;
    private final InstituicaoMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<Instituicao> findAll() {
        return repository
                .findAllByOrderByNomeAsc()
                .stream()
                .map(mapper::toModel)
                .toList();
    }
}
