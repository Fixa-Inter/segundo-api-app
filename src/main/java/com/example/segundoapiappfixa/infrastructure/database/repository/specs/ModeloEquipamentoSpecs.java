package com.example.segundoapiappfixa.infrastructure.database.repository.specs;

import com.example.segundoapiappfixa.infrastructure.database.entity.*;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public final class ModeloEquipamentoSpecs {

    public static Specification<ModeloEquipamentoEntity> findByEstaAtivo() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("estaAtivo"));
    }

    public static Specification<ModeloEquipamentoEntity> findByEnderecoId(Long id) {
        return (r, q, cb) -> cb.equal(r.join("usuario", JoinType.INNER).join("endereco", JoinType.INNER).get("id"), id);
    }

    public static Specification<ModeloEquipamentoEntity> findByNome(String v) {
        return like("nome", v);
    }

    public static Specification<ModeloEquipamentoEntity> findByDescricao(String v) {
        return like("descricao", v);
    }

    public static Specification<ModeloEquipamentoEntity> findByMarca(String v) {
        return relationLike("marcaEquipamento", "nome", v);
    }

    public static Specification<ModeloEquipamentoEntity> findByCategoria(String v) {
        return relationLike("categoriaEquipamento", "nome", v);
    }

    private static Specification<ModeloEquipamentoEntity> like(String campo, String v) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        return (r, q, cb) -> cb.like(cb.lower(r.get(campo)), "%" + v.toLowerCase() + "%");
    }

    private static Specification<ModeloEquipamentoEntity> relationLike(String relacao, String campo, String v) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        return (r, q, cb) -> cb.like(cb.lower(r.join(relacao, JoinType.INNER).get(campo)), "%" + v.toLowerCase() + "%");
    }
}
