package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.ProblemaMapper;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.domain.repository.ProblemaRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.ProblemaEntity;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaProblemaRepositiory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class ProblemaRepositoryImpl implements ProblemaRepository {

    // Dependências
    private final JpaProblemaRepositiory repository;
    private final ProblemaMapper mapper;


    // Método de listar os registros persistidos no banco de dados
    @Override
    public List<Problema> findAllByUsuarioId(Long usuarioId) {
        List<ProblemaEntity> problemas = repository.findAllByUsuario_Id(usuarioId);

        return problemas.stream()
                .map(mapper::toModel)
                .toList();
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public List<Problema> findAllByLocalEnderecoIds(List<Long> localEnderecoIds) {
        if (localEnderecoIds.isEmpty()) {
            return List.of();
        }

        return repository.findAllByLocalEndereco_IdIn(localEnderecoIds).stream()
                .map(mapper::toModel)
                .toList();
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public Optional<Problema> findById(Long problemaId) {
        return repository.findById(problemaId).map(mapper::toModel);
    }

    // Método de salvar no banco de dados
    @Override
    public Problema save(Problema problema) {
        ProblemaEntity problemaEntity = mapper.toEntity(problema);

        ProblemaEntity problemaPersistido = repository.save(problemaEntity);
        return mapper.toModel(problemaPersistido);
    }

}
