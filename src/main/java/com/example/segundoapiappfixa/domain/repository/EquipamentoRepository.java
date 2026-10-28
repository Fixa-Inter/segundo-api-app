package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosEquipamentoQueryParam;
import com.example.segundoapiappfixa.domain.model.Equipamento;

import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.Collection;

public interface EquipamentoRepository {

    Map<String, String> camposNormalizados = Map.of(
            "codigo", "codigo",
            "local_unidade", "localUnidade.nome",
            "usuario", "usuario.nomeCompleto"
    );

    List<Equipamento> findByModeloEquipamentoId(
            Long modeloEquipamentoId,
            FiltrosEquipamentoQueryParam filtros
    );

    Optional<Equipamento> findById(Long equipamentoId);

    Equipamento save(Equipamento equipamento);

    Optional<Equipamento> deleteById(Long equipamentoId);

    Map<Long, Long> countAtivosByModeloIds(Collection<Long> modeloIds);

}
