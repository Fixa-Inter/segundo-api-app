package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.OcorrenciaMapper;
import com.example.segundoapiappfixa.domain.model.Ocorrencia;
import com.example.segundoapiappfixa.domain.repository.OcorrenciaRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.EquipamentoEntity;
import com.example.segundoapiappfixa.infrastructure.database.entity.LocalEnderecoEntity;
import com.example.segundoapiappfixa.infrastructure.database.entity.OcorrenciaEntity;
import com.example.segundoapiappfixa.infrastructure.database.entity.UsuarioEntity;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaOcorrenciaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OcorrenciaRepositoryImpl implements OcorrenciaRepository {

    private final JpaOcorrenciaRepository jpaRepository;
    private final OcorrenciaMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Ocorrencia> findAllByUsuarioId(Long usuarioId) {
        return jpaRepository
                .findAllByUsuario_Id(usuarioId)
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public Optional<Ocorrencia> findById(Long id) {
        return jpaRepository
                .findById(id)
                .map(mapper::toModel);
    }

    @Override
    public Ocorrencia save(Ocorrencia ocorrencia) {
        OcorrenciaEntity entity = toEntityWithReferences(ocorrencia);
        return mapper.toModel(jpaRepository.save(entity));
    }

    @Override
    public Ocorrencia update(Long id, Ocorrencia ocorrencia) {
        OcorrenciaEntity entity = jpaRepository.findById(id).orElse(null);
        if (entity == null) return null;

        if (ocorrencia.getLocalEndereco() != null) {
            entity.setLocalEndereco(reference(LocalEnderecoEntity.class, ocorrencia.getLocalEndereco().getId()));
        }

        if (ocorrencia.getEquipamento() != null) {
            entity.setEquipamento(reference(EquipamentoEntity.class, ocorrencia.getEquipamento().getId()));
        }

        if (ocorrencia.getCategoriaProblema() != null) entity.setCategoriaProblema(ocorrencia.getCategoriaProblema());

        if (ocorrencia.getPrioridade() != null) entity.setPrioridade(ocorrencia.getPrioridade());

        if (ocorrencia.getTitulo() != null) entity.setTitulo(ocorrencia.getTitulo());

        if (ocorrencia.getDescricaoOcorrencia() != null) entity.setDescricaoOcorrencia(ocorrencia.getDescricaoOcorrencia());

        if (ocorrencia.getDescricaoLocal() != null) entity.setDescricaoLocal(ocorrencia.getDescricaoLocal());

        return mapper.toModel(jpaRepository.save(entity));
    }

    @Override
    public Optional<Ocorrencia> deleteById(Long id) {
        OcorrenciaEntity entity = jpaRepository.findById(id).orElse(null);
        if (entity == null) return Optional.empty();

        jpaRepository.deleteById(id);
        return Optional.of(mapper.toModel(entity));
    }

    private OcorrenciaEntity toEntityWithReferences(Ocorrencia ocorrencia) {
        OcorrenciaEntity entity = mapper.toEntity(ocorrencia);

        entity.usuario = reference(UsuarioEntity.class, ocorrencia.getUsuario().getId());

        entity.localEndereco = reference(LocalEnderecoEntity.class, ocorrencia.getLocalEndereco().getId());

        if (ocorrencia.getEquipamento() != null) {
            entity.equipamento = reference(EquipamentoEntity.class, ocorrencia.getEquipamento().getId());
        }
        return entity;
    }

    private <T> T reference(Class<T> type, Long id) {
        return entityManager.getReference(type, id);
    }
}
