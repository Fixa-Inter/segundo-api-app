package com.example.segundoapiappfixa.infrastructure.database.sql.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.FotoMapper;
import com.example.segundoapiappfixa.domain.enums.TipoFoto;
import com.example.segundoapiappfixa.domain.model.Foto;
import com.example.segundoapiappfixa.domain.repository.FotoRepository;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.*;
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

            case OCORRENCIA -> {
                if (foto.getOcorrencia() != null && foto.getOcorrencia().getId() != null) {

                    fotoEntity.setOcorrencia(
                            entityManager.getReference(
                                    OcorrenciaEntity.class,
                                    foto.getOcorrencia().getId()
                            ));
                }
            }

            case USUARIO -> {
                if (foto.getUsuario() != null && foto.getUsuario().getId() != null) {
                    UsuarioEntity usuario = entityManager.getReference(
                            UsuarioEntity.class,
                            foto.getUsuario().getId()
                    );

                    fotoEntity.setUsuario(usuario);

                    // Foto de usuário é única: atualiza o registro existente.
                    fotoEntity = repository.findByUsuario_Id(usuario.getId())
                            .map(existente -> {
                                existente.setUrl(foto.getUrl());
                                existente.setEstaAtivo(foto.getEstaAtivo());
                                return existente;
                            })
                            .orElse(fotoEntity);
                }
            }
        }

        FotoEntity fotoPersistida = repository.save(fotoEntity);
        return mapper.toModel(fotoPersistida);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByProblemaId(Long problemaId) {
        repository.deleteByProblema_Id(problemaId);
    }

    @Override
    public void deleteByOcorrenciaId(Long ocorrenciaId) {
        repository.deleteByOcorrencia_Id(ocorrenciaId);
    }

    @Override
    public void deleteByIdAndOcorrenciaId(Long fotoId, Long ocorrenciaId) {
        repository.deleteByIdAndOcorrencia_Id(fotoId, ocorrenciaId);
    }

    public void deleteByOrdemServicoId(Long ordemServicoId) {
        repository.deleteByOrdemServico_Id(ordemServicoId);
    }
}
