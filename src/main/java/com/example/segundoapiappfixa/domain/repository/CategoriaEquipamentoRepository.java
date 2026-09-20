package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.CategoriaEquipamento;
import java.util.Optional;

public interface CategoriaEquipamentoRepository {

    Optional<CategoriaEquipamento> findById(Long id);
}
