package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.ProblemaMapper;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosProblemaQueryParam;
import com.example.segundoapiappfixa.infrastructure.database.repository.specs.ProblemaSpecs;
import com.example.segundoapiappfixa.infrastructure.database.repository.utils.SortUtils;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.domain.repository.ProblemaRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.ProblemaEntity;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaProblemaRepositiory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

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
    public List<Problema> findAllByUsuarioId(Long usuarioId, FiltrosProblemaQueryParam filtros) {
        return buscar(ProblemaSpecs.findByUsuarioId(usuarioId), filtros);
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public List<Problema> findAllByLocalEnderecoIds(List<Long> localEnderecoIds, FiltrosProblemaQueryParam filtros) {

        if (localEnderecoIds.isEmpty()) {
            return List.of();
        }

        return buscar(ProblemaSpecs.findByLocalEnderecoIds(localEnderecoIds), filtros);
    }

    private List<Problema> buscar(Specification<ProblemaEntity> base, FiltrosProblemaQueryParam filtros) {
        Specification<ProblemaEntity> specification = base
                .and(ProblemaSpecs.findByTitulo(filtros.titulo()))
                .and(ProblemaSpecs.findByDescricaoProblema(filtros.descricaoProblema()))
                .and(ProblemaSpecs.findByCategoriaEquipamento(filtros.categoriaEquipamento()))
                .and(ProblemaSpecs.findByLocalEndereco(filtros.localEndereco()))
                .and(ProblemaSpecs.findByDescricaoLocal(filtros.descricaoLocal()))
                .and(ProblemaSpecs.findByNomeUsuario(filtros.nomeUsuario()))
                .and(ProblemaSpecs.findByTipoAcesso(filtros.tipoAcesso()))
                .and(ProblemaSpecs.findByDataCriacaoMin(filtros.dataCriacaoMin()))
                .and(ProblemaSpecs.findByDataCriacaoMax(filtros.dataCriacaoMax()));

        Sort sort = SortUtils.definirSort(
                SortUtils.normalizarCampoOrdenacao(filtros.campoOrdenacao(), camposNormalizados),
                filtros.direcaoOrdenacao()
        );

        Pageable pageable = filtros.limite() == null ? null :
                PageRequest.of(0, filtros.limite(), sort);

        List<ProblemaEntity> entidades = pageable != null
                ? repository.findAll(specification, pageable).getContent()
                : repository.findAll(specification, sort);

        return entidades
                .stream()
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
