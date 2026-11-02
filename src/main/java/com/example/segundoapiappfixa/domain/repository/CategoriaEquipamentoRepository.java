package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.filter.FiltrosCategoriaEquipamento;
import com.example.segundoapiappfixa.domain.model.CategoriaEquipamento;

import java.util.List;
import java.util.Optional;

public interface CategoriaEquipamentoRepository {

    CategoriaEquipamento save(CategoriaEquipamento categoriaEquipamento);

    Optional<CategoriaEquipamento> findById(Long categoriaEquipamentoId);

    Optional<CategoriaEquipamento> deleteById(Long categoriaEquipamentoId);

    List<CategoriaEquipamento> findByUnidadeId(
            Long categoriaEquipamentoId,
            FiltrosCategoriaEquipamento filtros
    );
}
