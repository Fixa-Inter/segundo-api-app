package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.MarcaEquipamentoMapper;
import com.example.segundoapiappfixa.domain.model.MarcaEquipamento;
import com.example.segundoapiappfixa.domain.repository.MarcaEquipamentoRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.MarcaEquipamentoEntity;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaMarcaEquipamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MarcaEquipamentoRepositoryImpl implements MarcaEquipamentoRepository {
    private final JpaMarcaEquipamentoRepository repository;
    private final MarcaEquipamentoMapper mapper;

    public List<MarcaEquipamento> findAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    public Optional<MarcaEquipamento> findById(Long id) {
        return repository
                .findById(id)
                .map(mapper::toModel);
    }

    public boolean existsByNomeIgnoreCase(String nome) {
        return repository.existsByNomeIgnoreCase(nome);
    }

    public boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id) {
        return repository.existsByNomeIgnoreCaseAndIdNot(nome, id);
    }

    public MarcaEquipamento save(MarcaEquipamento model) {
        return mapper.toModel(repository.save(mapper.toEntity(model)));
    }

    public Optional<MarcaEquipamento> deleteById(Long id) {

        Optional<MarcaEquipamentoEntity> entity = repository.findById(id);
        if (entity.isEmpty()) return Optional.empty();

        repository.deleteById(id);
        return entity.map(mapper::toModel);
    }
}
