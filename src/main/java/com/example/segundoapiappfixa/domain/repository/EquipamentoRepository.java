package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Equipamento;

import java.util.Optional;

public interface EquipamentoRepository {

    Optional<Equipamento> findById(Long id);

}
