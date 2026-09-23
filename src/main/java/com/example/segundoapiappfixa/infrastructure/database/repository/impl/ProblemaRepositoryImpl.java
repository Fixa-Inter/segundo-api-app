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
    private final JpaProblemaRepositiory problemaRepositiory;
    private final ProblemaMapper problemaMapper;


    // Método de listar os registros persistidos no banco de dados
    @Override
    public List<Problema> findAllByUsuarioId(Long usuarioId) {
        List<ProblemaEntity> problemas = problemaRepositiory.findAllByUsuario_Id(usuarioId);

        return problemas.stream()
                .map(problemaMapper::toModel)
                .toList();
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public List<Problema> findAllByLocalEnderecoIds(List<Long> localEnderecoIds) {
        if (localEnderecoIds.isEmpty()) {
            return List.of();
        }

        return problemaRepositiory.findAllByLocalEndereco_IdIn(localEnderecoIds).stream()
                .map(problemaMapper::toModel)
                .toList();
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public Optional<Problema> findById(Long problemaId) {
        return problemaRepositiory.findById(problemaId).map(problemaMapper::toModel);
    }

    // Método de salvar no banco de dados
    @Override
    public Problema save(Problema problema) {
        ProblemaEntity problemaEntity = problemaMapper.toEntity(problema);

        ProblemaEntity problemaPersistido = problemaRepositiory.save(problemaEntity);
        return problemaMapper.toModel(problemaPersistido);
    }

}
