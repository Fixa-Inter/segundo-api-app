package com.example.segundoapiappfixa.infrastructure.database.sql.repository.impl;

import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.UsuarioEntity;
import com.example.segundoapiappfixa.adapters.mapper.UsuarioMapper;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosUsuarioQueryParam;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.specs.UsuarioSpecs;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.utils.SortUtils;
import com.example.segundoapiappfixa.infrastructure.database.sql.repository.JpaUsuarioRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@Repository
@Transactional
@RequiredArgsConstructor
public class UsuarioRepositoryImpl implements UsuarioRepository {

    // Dependências
    private final JpaUsuarioRepository repository;
    private final UsuarioMapper mapper;

    // Método de listar os registros persistidos no banco de dados
    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByEmail(String email) {
        UsuarioEntity entity = repository.findUsuarioByEmail(email);
        return Optional.ofNullable(entity).map(mapper::toModel);
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id) {
        return repository
                .findOne(Specification.where(UsuarioSpecs.findByEstaAtivo())
                        .and((root, query, cb) -> cb.equal(root.get("id"), id)))
                .map(mapper::toModel);
    }

    // Método de listar todos os usuários de um endereço e tipo de acesso específico persistidos no banco de dados
    @Transactional(readOnly = true)
    public List<Usuario> findByEnderecoIdAndTipoAcesso(
            Long enderecoId,
            TipoAcesso tipoAcesso,
            FiltrosUsuarioQueryParam filtros
    ) {
        Specification<UsuarioEntity> specification = Specification.where(UsuarioSpecs.findByEstaAtivo())
                .and(UsuarioSpecs.findByEnderecoId(enderecoId))
                .and(UsuarioSpecs.findByTipoAcesso(tipoAcesso))
                .and(UsuarioSpecs.findByNomeCompleto(filtros.nomeCompleto()))
                .and(UsuarioSpecs.findByEmail(filtros.email()))
                .and(UsuarioSpecs.findByCargo(filtros.cargo()))
                .and(UsuarioSpecs.findByCnpjEndereco(filtros.cnpjEndereco()))
                .and(UsuarioSpecs.findByEstaAtivo());

        Sort sort = SortUtils.definirSort(
                SortUtils.normalizarCampoOrdenacao(filtros.campoOrdenacao(), camposNormalizados),
                filtros.direcaoOrdenacao()
        );

        Pageable pageable = filtros.limite() == null ? null :
                PageRequest.of(0, filtros.limite(), sort);

        List<UsuarioEntity> usuarioEntities = pageable != null
                ? repository.findAll(specification, pageable).getContent()
                : repository.findAll(specification, sort);

        return usuarioEntities
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    public Usuario save(Usuario usuario) {
        UsuarioEntity usuarioEntity = mapper.toEntity(usuario);
        return mapper.toModel(repository.save(usuarioEntity));
    }
}
