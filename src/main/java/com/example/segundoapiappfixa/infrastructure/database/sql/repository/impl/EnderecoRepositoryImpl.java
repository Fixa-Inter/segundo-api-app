package com.example.segundoapiappfixa.infrastructure.database.sql.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.EnderecoMapper;
import com.example.segundoapiappfixa.domain.model.Endereco;
import com.example.segundoapiappfixa.domain.repository.EnderecoRepository;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.JpaEnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class EnderecoRepositoryImpl implements EnderecoRepository {
    private final JpaEnderecoRepository repository;
    private final EnderecoMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<Endereco> findAllByInstituicaoId(Long instituicaoId) {
        return repository
                .findAllByInstituicao_IdOrderByLogradouroAscNumeroAsc(instituicaoId)
                .stream()
                .map(mapper::toModel)
                .toList();
    }
}
