package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.MarcaEquipamento;
import java.util.List;
import java.util.Optional;

public interface MarcaEquipamentoRepository {
    List<MarcaEquipamento> findAll();
    Optional<MarcaEquipamento> findById(Long id);
    boolean existsByNomeIgnoreCase(String nome);
    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);
    MarcaEquipamento save(MarcaEquipamento marcaEquipamento);
    Optional<MarcaEquipamento> deleteById(Long id);
}
