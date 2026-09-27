package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosCategoriaEquipamentoQueryParam;
import com.example.segundoapiappfixa.domain.model.CategoriaEquipamento;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CategoriaEquipamentoRepository {

    Map<String, String> camposNormalizados = Map.of(
            "nome", "nome",
            "descricao", "descricao",
            "nome_usuario", "usuario.nomeCompleto"
    );

    CategoriaEquipamento save(CategoriaEquipamento categoriaEquipamento);

    Optional<CategoriaEquipamento> findById(Long categoriaEquipamentoId);

    Optional<CategoriaEquipamento> deleteById(Long categoriaEquipamentoId);

    List<CategoriaEquipamento> findByEnderecoId(
            Long categoriaEquipamentoId,
            FiltrosCategoriaEquipamentoQueryParam filtros
    );
}
