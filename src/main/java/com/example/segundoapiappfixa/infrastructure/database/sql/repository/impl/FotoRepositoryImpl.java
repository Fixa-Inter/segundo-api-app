package com.example.segundoapiappfixa.infrastructure.database.sql.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.FotoMapper;
import com.example.segundoapiappfixa.domain.enums.TipoFoto;
import com.example.segundoapiappfixa.domain.model.Foto;
import com.example.segundoapiappfixa.domain.repository.FotoRepository;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.FotoEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.ObservacaoConclusaoEntity;
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
public class FotoRepositoryImpl implements FotoRepository {

    // Dependências
    private final JpaFotoRepository repository;
    private final FotoMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    // Método de listar os registros persistidos no banco de dados
    @Override
    public List<Foto> findAllByProblemaId(Long problemaId) {
        return repository.
                findByProblema_Id(problemaId)
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public List<Foto> findaAllByObservacaoConclusaoId(Long observacaoConclusao) {
        return repository.
                findByObservacaoConclusao_Id(observacaoConclusao)
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public void deleteAllByObservacaoConclusaoId(Long observacaoConclusaoId) {
        repository.deleteByObservacaoConclusao_Id(observacaoConclusaoId);
    }

    // Método de salvar no banco de dados
    @Override
    public Foto save(Foto foto, TipoFoto tipoFoto) {
        FotoEntity fotoEntity = mapper.toEntity(foto);

        switch (tipoFoto) {

            case PROBLEMA -> {
                if (foto.getProblema() != null && foto.getProblema().getId() != null) {

                    fotoEntity.setProblema(
                            entityManager.getReference(
                                    ProblemaEntity.class,
                                    foto.getProblema().getId()
                            ));
                }
            }

            case OBSERVACAO_CONCLUSAO -> {
                if (foto.getObservacaoConclusao() != null && foto.getObservacaoConclusao().getId() != null) {

                    fotoEntity.setObservacaoConclusao(
                            entityManager.getReference(
                                    ObservacaoConclusaoEntity.class,
                                    foto.getObservacaoConclusao().getId()
                            ));
                }
            }
        }

        FotoEntity fotoPersistida = repository.save(fotoEntity);
        return mapper.toModel(fotoPersistida);
    }
}
