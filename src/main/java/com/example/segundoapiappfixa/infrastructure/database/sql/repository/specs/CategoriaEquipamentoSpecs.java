package com.example.segundoapiappfixa.infrastructure.database.sql.repository.specs;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.CategoriaEquipamentoEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.UnidadeEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.UsuarioEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class CategoriaEquipamentoSpecs {

    public static Specification<CategoriaEquipamentoEntity> findByEstaAtivo() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("estaAtivo"));
    }

    public static Specification<CategoriaEquipamentoEntity> findByUnidadeId(Long unidadeId) {
        if (unidadeId == null) return Specification.unrestricted();

        return (root, query, criteriaBuilder) -> {

            Join<CategoriaEquipamentoEntity, UsuarioEntity> usuario = root.join(
                    "usuario",
                    JoinType.INNER
            );

            Join<UsuarioEntity, UnidadeEntity> unidade = usuario.join(
                    "unidade",
                    JoinType.INNER
            );

            return criteriaBuilder.equal(unidade.get("id"), unidadeId);
        };
    }

    public static Specification<CategoriaEquipamentoEntity> findByNome(String nome) {
        if (nome == null || nome.isBlank()) return Specification.unrestricted();

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("nome")),
                    "%" + nome.toLowerCase() + "%"
            );
    }

    public static Specification<CategoriaEquipamentoEntity> findByDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) return Specification.unrestricted();

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("descricao")),
                        "%" + descricao.toLowerCase() + "%"
                );
    }

    public static Specification<CategoriaEquipamentoEntity> findByNomeUsuario(String nomeUsuario) {
        if (nomeUsuario == null || nomeUsuario.isBlank()) return Specification.unrestricted();

        return (root, query, criteriaBuilder) -> {
            Join<CategoriaEquipamentoEntity, UsuarioEntity> usuario = root.join(
                    "usuario",
                    JoinType.INNER
            );

            return criteriaBuilder.like(
                    criteriaBuilder.lower(usuario.get("nomeCompleto")),
                    "%" + nomeUsuario.toLowerCase() + "%"
            );
        };
    }
}
