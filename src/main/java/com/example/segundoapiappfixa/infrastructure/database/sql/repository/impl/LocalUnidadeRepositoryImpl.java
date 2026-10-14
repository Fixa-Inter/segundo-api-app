package com.example.segundoapiappfixa.infrastructure.database.sql.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.LocalUnidadeMapper;
import com.example.segundoapiappfixa.domain.model.LocalUnidade;
import com.example.segundoapiappfixa.domain.repository.LocalUnidadeRepository;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.LocalUnidadeEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.JpaLocalUnidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LocalUnidadeRepositoryImpl implements LocalUnidadeRepository {

    // Dependências
    private final JpaLocalUnidadeRepository localUnidadeRepository;
    private final LocalUnidadeMapper localUnidadeMapper;

    // Método de listar os registros persistidos no banco de dados
    @Override
    public List<LocalUnidade> findAllByUnidadeId(Long unidadeId) {
        List<LocalUnidadeEntity> localUnidadeEntities = localUnidadeRepository.findAllByUnidade_Id(unidadeId);

        return localUnidadeEntities.stream()
                .map(localUnidadeMapper::toModel)
                .toList();
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public Optional<LocalUnidade> findById(Long id) {
        return localUnidadeRepository
                .findById(id)
                .map(localUnidadeMapper::toModel);
    }
}
