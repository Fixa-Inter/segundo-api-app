package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.MarcaEquipamentoMapper;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosMarcaEquipamentoQueryParam;
import com.example.segundoapiappfixa.infrastructure.database.repository.specs.MarcaEquipamentoSpecs;
import com.example.segundoapiappfixa.infrastructure.database.repository.utils.SortUtils;
import com.example.segundoapiappfixa.domain.model.MarcaEquipamento;
import com.example.segundoapiappfixa.domain.repository.MarcaEquipamentoRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.MarcaEquipamentoEntity;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaMarcaEquipamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Repository
@RequiredArgsConstructor
public class MarcaEquipamentoRepositoryImpl implements MarcaEquipamentoRepository {
    // Dependências
    private final JpaMarcaEquipamentoRepository repository;
    private final MarcaEquipamentoMapper mapper;

    // Método de listar os registros persistidos no banco de dados
    public List<MarcaEquipamento> findAll(FiltrosMarcaEquipamentoQueryParam filtros) {
        Specification<MarcaEquipamentoEntity> specification = Specification.where(MarcaEquipamentoSpecs.findByNome(filtros.nome()))
                .and(MarcaEquipamentoSpecs.findByDescricao(filtros.descricao()))
                .and(MarcaEquipamentoSpecs.findByEstaAtivo());

        Sort sort = SortUtils.definirSort(
                SortUtils.normalizarCampoOrdenacao(filtros.campoOrdenacao(), camposNormalizados),
                filtros.direcaoOrdenacao()
        );

        Pageable pageable = filtros.limite() == null ? null :
                        PageRequest.of(0, filtros.limite(), sort);

        List<MarcaEquipamentoEntity> marcas =
                pageable != null
                        ? repository.findAll(specification, pageable).getContent() :
                        repository.findAll(specification, sort);

        return marcas
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    // Método de listar os registros persistidos no banco de dados
    public Optional<MarcaEquipamento> findById(Long id) {
        return repository
                .findOne(Specification.where(MarcaEquipamentoSpecs.findByEstaAtivo())
                        .and((root, query, cb) -> cb.equal(root.get("id"), id)))
                .map(mapper::toModel);
    }

    public boolean existsByNomeIgnoreCase(String nome) {
        return repository.existsByNomeIgnoreCase(nome);
    }

    public boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id) {
        return repository.existsByNomeIgnoreCaseAndIdNot(nome, id);
    }

    // Método de salvar no banco de dados
    public MarcaEquipamento save(MarcaEquipamento model) {
        return mapper.toModel(repository.save(mapper.toEntity(model)));
    }

    // Método de deletar dados persistidos no banco de dados
    public Optional<MarcaEquipamento> deleteById(Long id) {

        Optional<MarcaEquipamentoEntity> entity = repository.findById(id);
        if (entity.isEmpty()) return Optional.empty();

        entity.get().setEstaAtivo(false);
        repository.save(entity.get());
        return entity.map(mapper::toModel);
    }
}
