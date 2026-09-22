package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.EquipamentoMapper;
import com.example.segundoapiappfixa.domain.model.Equipamento;
import com.example.segundoapiappfixa.domain.repository.EquipamentoRepository;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaEquipamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EquipamentoRepositoryImpl implements EquipamentoRepository {

    // Dependências
    private final JpaEquipamentoRepository repository;
    private final EquipamentoMapper mapper;

    // Método de listar os registros persistidos no banco de dados
    public Optional<Equipamento> findById(Long id) {
        return repository
                .findById(id)
                .map(mapper::toModel);
    }

}
