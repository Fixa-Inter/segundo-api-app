package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Equipamento;

import java.util.Optional;
import java.util.List;

public interface EquipamentoRepository {

    List<Equipamento> findByModeloEquipamentoId(Long modeloEquipamentoId);

    Optional<Equipamento> findById(Long id);

    Equipamento save(Equipamento equipamento);

    Optional<Equipamento> deleteById(Long id);

}
