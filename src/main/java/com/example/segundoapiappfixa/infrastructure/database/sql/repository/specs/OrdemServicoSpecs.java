package com.example.segundoapiappfixa.infrastructure.database.sql.repository.specs;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.*;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.OrdemServicoEntity;
import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.Prioridade;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
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
        return (r, q, c) -> c.like(
                c.lower(
                        r.join("problema", JoinType.INNER)
                                .join("categoriaEquipamento", JoinType.INNER)
                                .get("nome")
                ),
                "%" + v.toLowerCase() + "%"
        );
    }

    public static Specification<OrdemServicoEntity> findByCategoriaProblema(String v) {
        return enumLike("categoriaProblema", v, CategoriaProblema.class);
    }

    public static Specification<OrdemServicoEntity> findByPrioridade(String v) {
        return enumLike("prioridade", v, Prioridade.class);
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
        return enumRelationLike("usuario", "tipoAcesso", v, TipoAcesso.class);
    }

    private static Specification<OrdemServicoEntity> enumLike(String f, String v, Class<? extends Enum<?>> enumType) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        Enum<?> value = enumValue(enumType, v);
        if (value == null) return Specification.unrestricted();
        return (r, q, c) -> c.equal(r.get(f), value);
    }

    private static Specification<OrdemServicoEntity> enumRelationLike(String rel, String field, String v, Class<? extends Enum<?>> enumType) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        Enum<?> value = enumValue(enumType, v);
        if (value == null) return Specification.unrestricted();
        return (r, q, c) -> c.equal(r.join(rel, JoinType.INNER).get(field), value);
    }

    private static Enum<?> enumValue(Class<? extends Enum<?>> enumType, String value) {
        try {
            if (enumType == CategoriaProblema.class) return CategoriaProblema.fromNome(value);
            if (enumType == Prioridade.class) return Prioridade.fromNome(value);
            if (enumType == TipoAcesso.class) return TipoAcesso.fromNome(value);
            return Enum.valueOf(enumType.asSubclass(Enum.class), value);
        } catch (IllegalArgumentException exception) {
            return null;
        }
    }

    private static Specification<OrdemServicoEntity> relationLike(String rel, String f, String v) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        return (r, q, c) -> c.like(c.lower(r.join(rel, JoinType.INNER).get(f)), "%" + v.toLowerCase() + "%");
    }
}
