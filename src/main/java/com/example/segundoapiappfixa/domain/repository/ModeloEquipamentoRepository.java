package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.ModeloEquipamento;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosModeloEquipamentoQueryParam;
import java.util.Map;
import java.util.List;
import java.util.Optional;

public interface ModeloEquipamentoRepository {
    Map<String, String> camposNormalizados = Map.of(
            "nome", "nome",
            "descricao", "descricao",
            "marca", "marcaEquipamento.nome",
            "categoria", "categoriaEquipamento.nome"
    );

    List<ModeloEquipamento> findByEnderecoId(
            Long enderecoId,
            FiltrosModeloEquipamentoQueryParam filtros
    );

    Optional<ModeloEquipamento> findById(Long modeloEquipamentoId);

    ModeloEquipamento save(ModeloEquipamento modeloEquipamento);

    Optional<ModeloEquipamento> deleteById(Long modeloEquipamentoId);
}
