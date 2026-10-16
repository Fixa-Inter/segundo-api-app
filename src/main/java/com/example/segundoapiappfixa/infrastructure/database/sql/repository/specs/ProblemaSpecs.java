package com.example.segundoapiappfixa.infrastructure.database.sql.repository.specs;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.*;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.ProblemaEntity;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.time.LocalDateTime;

public final class ProblemaSpecs {
    private ProblemaSpecs() {
    }

    public static Specification<ProblemaEntity> findByUsuarioId(Long v) {
        return (r, q, c) -> c.equal(r.join("usuario", JoinType.INNER).get("id"), v);
    }

    public static Specification<ProblemaEntity> findByLocalUnidadeIds(List<Long> v) {
        return (r, q, c) -> r.join("localUnidade", JoinType.INNER).get("id").in(v);
    }

    public static Specification<ProblemaEntity> findByTitulo(String v) {
        return like("titulo", v);
    }

    public static Specification<ProblemaEntity> findByDescricaoProblema(String v) {
        return like("descricaoProblema", v);
    }

    public static Specification<ProblemaEntity> findByDescricaoLocal(String v) {
        return like("descricaoLocal", v);
    }

    public static Specification<ProblemaEntity> findByCategoriaEquipamento(String v) {
        return relationLike("categoriaEquipamento", "nome", v);
    }

    public static Specification<ProblemaEntity> findByLocalUnidade(String v) {
        return relationLike("localUnidade", "nome", v);
    }

    public static Specification<ProblemaEntity> findByNomeUsuario(String v) {
        return relationLike("usuario", "nomeCompleto", v);
    }

    public static Specification<ProblemaEntity> findByTipoAcesso(String v) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        final TipoAcesso tipoAcesso;
        try {
            tipoAcesso = TipoAcesso.fromNome(v);
        } catch (IllegalArgumentException exception) {
            return Specification.unrestricted();
        }
        return (r, q, c) -> c.equal(r.join("usuario", JoinType.INNER).get("tipoAcesso"), tipoAcesso);
    }

    public static Specification<ProblemaEntity> findByDataCriacaoMin(LocalDateTime v) {
        if (v == null) return Specification.unrestricted();
        return (r, q, c) -> c.greaterThanOrEqualTo(r.get("dataCriacao"), v);
    }

    public static Specification<ProblemaEntity> findByDataCriacaoMax(LocalDateTime v) {
        if (v == null) return Specification.unrestricted();
        return (r, q, c) -> c.lessThanOrEqualTo(r.get("dataCriacao"), v);
    }

    private static Specification<ProblemaEntity> like(String f, String v) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        return (r, q, c) -> c.like(c.lower(r.get(f)), "%" + v.toLowerCase() + "%");
    }

    private static Specification<ProblemaEntity> relationLike(String rel, String f, String v) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        return (r, q, c) -> c.like(c.lower(r.join(rel, JoinType.INNER).get(f)), "%" + v.toLowerCase() + "%");
    }
}
