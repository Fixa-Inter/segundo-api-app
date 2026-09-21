package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.ModeloEquipamento;
import java.util.List;
import java.util.Optional;

public interface ModeloEquipamentoRepository {
    List<ModeloEquipamento> findByEnderecoId(Long enderecoId);
    Optional<ModeloEquipamento> findById(Long id);
    ModeloEquipamento save(ModeloEquipamento modeloEquipamento);
    Optional<ModeloEquipamento> deleteById(Long id);
}
