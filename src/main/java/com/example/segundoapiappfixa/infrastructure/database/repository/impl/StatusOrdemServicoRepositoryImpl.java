package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.StatusOrdemServicoMapper;
import com.example.segundoapiappfixa.domain.model.StatusOrdemServico;
import com.example.segundoapiappfixa.domain.repository.StatusOrdemServicoRepository;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaStatusOrdemServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StatusOrdemServicoRepositoryImpl implements StatusOrdemServicoRepository {

    // Dependências
    private final JpaStatusOrdemServicoRepository repository;
    private final StatusOrdemServicoMapper mapper;

    // Método de listar os registros persistidos no banco de dados
    @Override
    public Optional<StatusOrdemServico> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toModel);

    }
}
