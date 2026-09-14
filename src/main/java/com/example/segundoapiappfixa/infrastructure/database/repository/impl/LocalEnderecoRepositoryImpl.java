package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.LocalEnderecoMapper;
import com.example.segundoapiappfixa.domain.model.LocalEndereco;
import com.example.segundoapiappfixa.domain.repository.LocalEnderecoRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.LocalEnderecoEntity;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaLocalEnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LocalEnderecoRepositoryImpl implements LocalEnderecoRepository {

    private final JpaLocalEnderecoRepository localEnderecoRepository;
    private final LocalEnderecoMapper localEnderecoMapper;

    @Override
    public List<LocalEndereco> findAllByEnderecoId(Long enderecoId) {
        List<LocalEnderecoEntity> localEnderecoEntities = localEnderecoRepository.findAllByEndereco_Id(enderecoId);

        return localEnderecoEntities.stream()
                .map(localEnderecoMapper::toModel)
                .toList();
    }

    @Override
    public LocalEndereco findById(Long id) {
        LocalEnderecoEntity localEnderecoEntity = localEnderecoRepository.findById(id)
                .orElseThrow();

        return localEnderecoMapper.toModel(localEnderecoEntity);
    }
}
