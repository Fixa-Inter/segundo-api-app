package com.example.segundoapiappfixa.infrastructure.database.sql.repository.specs;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.*;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.OrdemServicoEntity;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;

public final class OrdemServicoSpecs {
    private OrdemServicoSpecs() {
    }

    public static Specification<OrdemServicoEntity> findByProblemaIds(List<Long> v) {
        return (r, q, c) -> r.join("problema", JoinType.INNER).get("id").in(v);
    }

    public static Specification<OrdemServicoEntity> findByUsuarioId(Long v) {
        return (r, q, c) -> c.equal(r.join("usuario", JoinType.INNER).get("id"), v);
    }

    public static Specification<OrdemServicoEntity> findByTitulo(String v) {
        return relationLike("problema", "titulo", v);
    }

    public static Specification<OrdemServicoEntity> findByDescricao(String v) {
        return relationLike("problema", "descricaoProblema", v);
    }

    public static Specification<OrdemServicoEntity> findByCategoriaEquipamento(String v) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        return (r, q, c) -> c.like(c.lower(r.join("problema", JoinType.INNER).join("categoriaEquipamento", JoinType.INNER).get("nome")), "%" + v.toLowerCase() + "%");
    }

    public static Specification<OrdemServicoEntity> findByCategoriaProblema(String v) {
        return enumLike("categoriaProblema", v);
    }

    public static Specification<OrdemServicoEntity> findByPrioridade(String v) {
        return enumLike("prioridade", v);
    }

    public static Specification<OrdemServicoEntity> findByDataPrevistaMin(LocalDateTime v) {
        if (v == null) return Specification.unrestricted();
        return (r, q, c) -> c.greaterThanOrEqualTo(r.get("dataPrevista"), v);
    }

    public static Specification<OrdemServicoEntity> findByDataPrevistaMax(LocalDateTime v) {
        if (v == null) return Specification.unrestricted();
        return (r, q, c) -> c.lessThanOrEqualTo(r.get("dataPrevista"), v);
    }

    public static Specification<OrdemServicoEntity> findByNomeUsuario(String v) {
        return relationLike("usuario", "nomeCompleto", v);
    }

    public static Specification<OrdemServicoEntity> findByTipoAcesso(String v) {
        return relationLike("usuario", "tipoAcesso", v);
    }

    private static Specification<OrdemServicoEntity> enumLike(String f, String v) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        return (r, q, c) -> c.equal(r.get(f), v);
    }

    private static Specification<OrdemServicoEntity> relationLike(String rel, String f, String v) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        return (r, q, c) -> c.like(c.lower(r.join(rel, JoinType.INNER).get(f)), "%" + v.toLowerCase() + "%");
    }
}
