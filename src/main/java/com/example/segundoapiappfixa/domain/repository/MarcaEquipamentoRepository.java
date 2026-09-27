package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.MarcaEquipamento;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosMarcaEquipamentoQueryParam;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MarcaEquipamentoRepository {
    Map<String, String> camposNormalizados = Map.of(
            "nome", "nome",
            "descricao", "descricao",
            "esta_ativo", "estaAtivo"
    );

    List<MarcaEquipamento> findAll(FiltrosMarcaEquipamentoQueryParam filtros);

    Optional<MarcaEquipamento> findById(Long marcaEquipamentoId);

    boolean existsByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long marcaEquipamentoId);

    MarcaEquipamento save(MarcaEquipamento marcaEquipamento);

    Optional<MarcaEquipamento> deleteById(Long marcaEquipamentoId);
}
