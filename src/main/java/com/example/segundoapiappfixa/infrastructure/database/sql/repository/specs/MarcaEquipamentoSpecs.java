package com.example.segundoapiappfixa.infrastructure.database.sql.repository.specs;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.MarcaEquipamentoEntity;
import org.springframework.data.jpa.domain.Specification;

public final class MarcaEquipamentoSpecs {

    public static Specification<MarcaEquipamentoEntity> findByEstaAtivo() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("estaAtivo"));
    }

    public static Specification<MarcaEquipamentoEntity> findByNome(String valor) {
        if (valor == null || valor.isBlank()) return Specification.unrestricted();

        return (r, q, cb) ->
                cb.like(
                        cb.lower(r.get("nome")),
                        "%" + valor.toLowerCase() + "%"
                );
    }
    public static Specification<MarcaEquipamentoEntity> findByDescricao(String valor) {
        if (valor == null || valor.isBlank()) return Specification.unrestricted();

        return (r, q, cb) ->
                cb.like(
                        cb.lower(r.get("descricao")),
                        "%" + valor.toLowerCase() + "%"
                );
    }
    public static Specification<MarcaEquipamentoEntity> findByEstaAtivo(Boolean valor) {
        if (valor == null) return Specification.unrestricted();

        return (r, q, cb) ->
                cb.equal(r.get("estaAtivo"), valor);
    }
}
