package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.CategoriaEquipamento;

import java.util.List;
import java.util.Optional;

public interface CategoriaEquipamentoRepository {

    CategoriaEquipamento save(CategoriaEquipamento categoriaEquipamento);


    Optional<CategoriaEquipamento> findById(Long id);

    Optional<CategoriaEquipamento> deleteById(Long id);

    List<CategoriaEquipamento> findByEnderecoId(Long id);
}
