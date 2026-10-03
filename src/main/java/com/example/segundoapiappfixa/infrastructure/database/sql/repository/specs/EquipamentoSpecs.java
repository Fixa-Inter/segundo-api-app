package com.example.segundoapiappfixa.infrastructure.database.sql.repository.specs;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.EquipamentoEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.LocalEnderecoEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.ModeloEquipamentoEntity;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.UsuarioEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class EquipamentoSpecs {

    public static Specification<EquipamentoEntity> findByEstaAtivo() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("estaAtivo"));
    }

    public static Specification<EquipamentoEntity> findByModeloEquipamentoId(Long modeloEquipamentoId) {
        if (modeloEquipamentoId == null) return Specification.unrestricted();

        return (root, query, criteriaBuilder) -> {
            Join<EquipamentoEntity, ModeloEquipamentoEntity> modeloEquipamentoEntityJoin = root.join(
                    "modeloEquipamento",
                    JoinType.INNER
            );

            return criteriaBuilder.equal(
                    modeloEquipamentoEntityJoin.get("id"),
                    modeloEquipamentoId
            );
        };
    }

    public static Specification<EquipamentoEntity> findByCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) return Specification.unrestricted();

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("codigo")),
                    "%" + codigo.toLowerCase() + "%"
                );
    }

    public static Specification<EquipamentoEntity> findByLocalEndereco(String localEndereco) {
        if (localEndereco == null || localEndereco.isBlank()) return Specification.unrestricted();

        return (root, query, criteriaBuilder) -> {
            Join<EquipamentoEntity, LocalEnderecoEntity> localEnderecoEntityJoin = root.join(
                    "localEndereco",
                    JoinType.INNER
            );

            return criteriaBuilder.like(
                    criteriaBuilder.lower(localEnderecoEntityJoin.get("nome")),
                    "%" + localEndereco.toLowerCase() + "%"
            );
        };
    }

    public static Specification<EquipamentoEntity> findByUsuario(String usuario) {
        if (usuario == null || usuario.isBlank()) return Specification.unrestricted();

        return (root, query, criteriaBuilder) -> {
            Join<EquipamentoEntity, UsuarioEntity> usuarioEntityJoin = root.join(
                    "usuario",
                    JoinType.INNER
            );

            return criteriaBuilder.like(
                    criteriaBuilder.lower(usuarioEntityJoin.get("nomeCompleto")),
                    "%" + usuario.toLowerCase() + "%"
            );
        };
    }

}
