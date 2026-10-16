package com.example.segundoapiappfixa.infrastructure.database.sql.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.UnidadeMapper;
import com.example.segundoapiappfixa.domain.model.Unidade;
import com.example.segundoapiappfixa.domain.repository.UnidadeRepository;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.JpaUnidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class UnidadeRepositoryImpl implements UnidadeRepository {
    private final JpaUnidadeRepository repository;
    private final UnidadeMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<Unidade> findAllByInstituicaoId(Long instituicaoId) {
        return repository
                .findAllByInstituicao_IdOrderByLogradouroAscNumeroAsc(instituicaoId)
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Unidade> findById(Long id) {
        return repository.findById(id).map(mapper::toModel);
    }
}
