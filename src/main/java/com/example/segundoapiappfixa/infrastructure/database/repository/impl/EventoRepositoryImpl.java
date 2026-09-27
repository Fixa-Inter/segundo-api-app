package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.EventoMapper;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosEventosQueryParam;
import com.example.segundoapiappfixa.infrastructure.database.repository.specs.EventoSpecs;
import com.example.segundoapiappfixa.infrastructure.database.repository.utils.SortUtils;
import com.example.segundoapiappfixa.domain.model.Evento;
import com.example.segundoapiappfixa.domain.repository.EventoRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.EventoEntity;
import com.example.segundoapiappfixa.infrastructure.database.entity.LocalEnderecoEntity;
import com.example.segundoapiappfixa.infrastructure.database.entity.UsuarioEntity;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaEventoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class EventoRepositoryImpl implements EventoRepository {

    // Dependências
    private final JpaEventoRepository repository;
    private final EventoMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    // Método de listar os registros persistidos no banco de dados
    @Override
    public List<Evento> findByUsuarioId(Long usuarioId, FiltrosEventosQueryParam filtros) {
        Specification<EventoEntity> specification = Specification.where(EventoSpecs.findByUsuarioId(usuarioId))
                .and(EventoSpecs.findByTitulo(filtros.titulo()))
                .and(EventoSpecs.findByDescricao(filtros.descricao()))
                .and(EventoSpecs.findByLocalEndereco(filtros.localEndereco()))
                .and(EventoSpecs.findByDescricaoLocal(filtros.descricaoLocal()))
                .and(EventoSpecs.findByDataHoraInicio(filtros.dataHoraInicio()))
                .and(EventoSpecs.findByDataHoraFim(filtros.dataHoraFim()))
                .and(EventoSpecs.findByObservacao(filtros.observacao()))
                .and(EventoSpecs.findByUsuario(filtros.usuario()));

        Sort sort = SortUtils.definirSort(
                SortUtils.normalizarCampoOrdenacao(filtros.campoOrdenacao(),camposNormalizados),
                filtros.direcaoOrdenacao()
        );

        Pageable pageable = filtros.limite() == null ? null :
                PageRequest.of(0, filtros.limite(), sort);

        List<EventoEntity> eventos =
                pageable != null
                        ? repository.findAll(specification, pageable).getContent()
                        : repository.findAll(specification, sort);

        return eventos
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public Optional<Evento> findById(Long id) {
        return repository.findById(id).map(mapper::toModel);
    }

    // Método de salvar no banco de dados
    @Override
    public Evento save(Evento evento) {
        EventoEntity entity = mapper.toEntity(evento);

        entity.usuario = entityManager.getReference(
                UsuarioEntity.class,
                evento.getUsuario().getId()
        );

        entity.localEndereco = entityManager.getReference(
                LocalEnderecoEntity.class,
                evento.getLocalEndereco().getId()
        );

        return mapper.toModel(repository.save(entity));
    }

    // Método de deletar dados persistidos no banco de dados
    @Override
    public Optional<Evento> deleteById(Long id) {
        EventoEntity entity = repository.findById(id).orElse(null);

        if (entity == null) return Optional.empty();

        repository.deleteById(id);
        return Optional.of(mapper.toModel(entity));
    }
}
