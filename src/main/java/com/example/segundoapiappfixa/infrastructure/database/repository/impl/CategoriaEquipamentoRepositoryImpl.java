package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.CategoriaEquipamentoMapper;
import com.example.segundoapiappfixa.domain.model.CategoriaEquipamento;
import com.example.segundoapiappfixa.domain.repository.CategoriaEquipamentoRepository;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaCategoriaEquipamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoriaEquipamentoRepositoryImpl implements CategoriaEquipamentoRepository {

    private final JpaCategoriaEquipamentoRepository categoriaEquipamentoRepository;
    private final CategoriaEquipamentoMapper mapper;

    public Optional<CategoriaEquipamento> findById(Long id) {
        return categoriaEquipamentoRepository
                .findById(id)
                .map(mapper::toModel);
    }
}
