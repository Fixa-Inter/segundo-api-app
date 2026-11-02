package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.ModeloEquipamento;
import com.example.segundoapiappfixa.domain.filter.FiltrosModeloEquipamento;
import java.util.List;
import java.util.Optional;

public interface ModeloEquipamentoRepository {
    List<ModeloEquipamento> findByUnidadeId(
            Long unidadeId,
            FiltrosModeloEquipamento filtros
    );

    Optional<ModeloEquipamento> findById(Long modeloEquipamentoId);

    ModeloEquipamento save(ModeloEquipamento modeloEquipamento);

    Optional<ModeloEquipamento> deleteById(Long modeloEquipamentoId);
}
