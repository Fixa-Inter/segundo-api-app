package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.CategoriaEquipamentoMapper;
import com.example.segundoapiappfixa.domain.model.CategoriaEquipamento;
import com.example.segundoapiappfixa.domain.repository.CategoriaEquipamentoRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.CategoriaEquipamentoEntity;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaCategoriaEquipamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoriaEquipamentoRepositoryImpl implements CategoriaEquipamentoRepository {

    private final JpaCategoriaEquipamentoRepository categoriaEquipamentoRepository;
    private final CategoriaEquipamentoMapper mapper;

    public CategoriaEquipamento findById(Long id) {
        CategoriaEquipamentoEntity categoriaEquipamentoEntity = categoriaEquipamentoRepository
                .findById(id).orElseThrow();

        return mapper.toModel(categoriaEquipamentoEntity);
    }
}
