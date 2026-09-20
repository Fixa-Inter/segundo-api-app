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

    private final JpaEquipamentoRepository jpaEquipamentoRepository;
    private final EquipamentoMapper equipamentoMapper;

    public Optional<Equipamento> findById(Long id) {
        return jpaEquipamentoRepository
                .findById(id)
                .map(equipamentoMapper::toModel);
    }

}
