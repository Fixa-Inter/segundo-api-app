package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.LocalEnderecoMapper;
import com.example.segundoapiappfixa.domain.model.LocalEndereco;
import com.example.segundoapiappfixa.domain.repository.LocalEnderecoRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.LocalEnderecoEntity;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaLocalEnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LocalEnderecoRepositoryImpl implements LocalEnderecoRepository {

    // Dependências
    private final JpaLocalEnderecoRepository localEnderecoRepository;
    private final LocalEnderecoMapper localEnderecoMapper;

    // Método de listar os registros persistidos no banco de dados
    @Override
    public List<LocalEndereco> findAllByEnderecoId(Long enderecoId) {
        List<LocalEnderecoEntity> localEnderecoEntities = localEnderecoRepository.findAllByEndereco_Id(enderecoId);

        return localEnderecoEntities.stream()
                .map(localEnderecoMapper::toModel)
                .toList();
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public Optional<LocalEndereco> findById(Long id) {
        return localEnderecoRepository
                .findById(id)
                .map(localEnderecoMapper::toModel);
    }
}
