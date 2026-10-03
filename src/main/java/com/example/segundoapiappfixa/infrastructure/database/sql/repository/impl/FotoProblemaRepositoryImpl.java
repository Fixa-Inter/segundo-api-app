package com.example.segundoapiappfixa.infrastructure.database.sql.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.FotoMapper;
import com.example.segundoapiappfixa.domain.model.Foto;
import com.example.segundoapiappfixa.domain.repository.FotoRepository;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.FotoEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.ProblemaEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.JpaFotoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class FotoProblemaRepositoryImpl implements FotoRepository {

    // Dependências
    private final JpaFotoRepository repository;
    private final FotoMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    // Método de salvar no banco de dados
    @Override
    public List<Foto> findAllByProblemaId(Long problemaId) {
        return repository.
                findByProblema_Id(problemaId)
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public Foto save(Foto foto) {
        FotoEntity fotoEntity = mapper.toEntity(foto);

        if (foto.getProblema() != null && foto.getProblema().getId() != null) {

            fotoEntity.setProblema(
                    entityManager.getReference(
                        ProblemaEntity.class,
                        foto.getProblema().getId()
                    ));
        }

        FotoEntity fotoPersistida = repository.save(fotoEntity);
        return mapper.toModel(fotoPersistida);
    }
}
