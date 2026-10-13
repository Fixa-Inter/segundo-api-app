package com.example.segundoapiappfixa.infrastructure.database.sql.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.ObservacaoConclusaoMapper;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.repository.ObservacaoConclusaoRepository;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosObservacaoConclusaoQueryParam;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.specs.ObservacaoConclusaoSpecs;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.*;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.JpaObservacaoConclusaoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class ObservacaoConclusaoRepositoryImpl implements ObservacaoConclusaoRepository {

    private final JpaObservacaoConclusaoRepository repository;

    private final ObservacaoConclusaoMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ObservacaoConclusao save(ObservacaoConclusao model) {
        ObservacaoConclusaoEntity observacaoConclusaoEntity = mapper.toEntity(model);
        observacaoConclusaoEntity.setOrdemServico(entityManager.getReference(
                OrdemServicoEntity.class, model.getOrdemServico().getId()));

        ObservacaoConclusaoEntity observacaoConclusaoPersistido = repository
                .save(observacaoConclusaoEntity);

        return mapper.toModel(observacaoConclusaoPersistido);
    }

    @Override
    public Optional<ObservacaoConclusao> findById(Long id) {
        return repository.findById(id).map(mapper::toModel);
    }

    @Override
    public boolean existsByOrdemServicoId(Long ordemServicoId) {
        return repository.existsByOrdemServico_Id(ordemServicoId);
    }

    @Override
    public List<ObservacaoConclusao> findAllByEnderecoId(Long id, FiltrosObservacaoConclusaoQueryParam f) {
        return buscar(ObservacaoConclusaoSpecs.findByEnderecoId(id), f);
    }

    @Override
    public List<ObservacaoConclusao> findAllByResponsavelId(Long id, FiltrosObservacaoConclusaoQueryParam f) {
        return buscar(ObservacaoConclusaoSpecs.findByResponsavelId(id), f);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private List<ObservacaoConclusao> buscar(org.springframework.data.jpa.domain.Specification<ObservacaoConclusaoEntity> base, FiltrosObservacaoConclusaoQueryParam f) {
        if (f != null) {
            base = base.and(ObservacaoConclusaoSpecs.equalId(f.id()))
                    .and(ObservacaoConclusaoSpecs.categoriaProblema(f.categoriaProblema()))
                    .and(ObservacaoConclusaoSpecs.prioridade(f.prioridade()))
                    .and(ObservacaoConclusaoSpecs.dificuldade(f.dificuldade()))
                    .and(ObservacaoConclusaoSpecs.like("observacao", f.observacao()))
                    .and(ObservacaoConclusaoSpecs.nestedLike("problema", "", "titulo", f.titulo()))
                    .and(ObservacaoConclusaoSpecs.nestedLike("problema", "", "descricaoProblema", f.descricaoProblema()))
                    .and(ObservacaoConclusaoSpecs.nestedLike("problema", "categoriaEquipamento", "nome", f.categoriaEquipamento()))
                    .and(ObservacaoConclusaoSpecs.nestedLike("problema", "localEndereco", "nome", f.localEndereco()))
                    .and(ObservacaoConclusaoSpecs.nestedLike("problema", "", "descricaoLocal", f.descricaoLocal()))
                    .and(ObservacaoConclusaoSpecs.nestedLike("problema", "status", "nome", f.statusProblema()))
                    .and(ObservacaoConclusaoSpecs.relationLike("usuario", "nomeCompleto", f.nomeResponsavel()))
                    .and(ObservacaoConclusaoSpecs.statusOrdemServico(f.statusOrdemServico()));
        }
        return repository.findAll(base).stream().map(mapper::toModel).toList();
    }
}
