package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.AptidaoMapper;
import com.example.segundoapiappfixa.domain.model.Aptidao;
import com.example.segundoapiappfixa.domain.repository.AptidaoRepository;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaAptidaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class AptidaoRepositoryImpl implements AptidaoRepository {

    private final JpaAptidaoRepository repository;
    private final AptidaoMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<Aptidao> findByUsuarioId(Long usuarioId) {
        return repository.findAllByUsuario_IdOrderByNotaDesc(usuarioId)
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Aptidao> findById(Long id) {
        return repository.findById(id).map(mapper::toModel);
    }

    @Override
    public Aptidao save(Aptidao aptidao) {
        return mapper.toModel(repository.save(mapper.toEntity(aptidao)));
    }
}
