package com.example.segundoapiappfixa.infrastructure.database.sql.repository.specs;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.ObservacaoConclusaoEntity;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.Prioridade;
import com.example.segundoapiappfixa.domain.enums.Dificuldade;
import com.example.segundoapiappfixa.domain.enums.StatusOrdemServico;

public final class ObservacaoConclusaoSpecs {
    private ObservacaoConclusaoSpecs() {
    }

    public static Specification<ObservacaoConclusaoEntity> findByUnidadeId(Long id) {
        return (r, q, c) -> c.equal(r.join("ordemServico", JoinType.INNER)
                .join("problema", JoinType.INNER)
                .join("localUnidade", JoinType.INNER)
                .join("unidade", JoinType.INNER).get("id"), id);
    }

    public static Specification<ObservacaoConclusaoEntity> findByResponsavelId(Long id) {
        return (r, q, c) -> c.equal(r.join("ordemServico", JoinType.INNER)
                .join("usuario", JoinType.INNER).get("id"), id);
    }

    public static Specification<ObservacaoConclusaoEntity> equalId(Long id) {
        return equal("id", id);
    }

    public static Specification<ObservacaoConclusaoEntity> categoriaProblema(String value) {
        return enumValue("categoriaProblema", value, CategoriaProblema.class);
    }

    public static Specification<ObservacaoConclusaoEntity> prioridade(String value) {
        return enumValue("prioridade", value, Prioridade.class);
    }

    public static Specification<ObservacaoConclusaoEntity> dificuldade(String value) {
        return enumValue("dificuldade", value, Dificuldade.class);
    }

    public static Specification<ObservacaoConclusaoEntity> like(String field, String value) {
        if (value == null || value.isBlank()) return Specification.unrestricted();
        return (r, q, c) -> c.like(c.lower(r.get(field)), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ObservacaoConclusaoEntity> relationLike(String relation, String field, String value) {
        if (value == null || value.isBlank()) return Specification.unrestricted();
        return (r, q, c) -> c.like(c.lower(r.join("ordemServico", JoinType.INNER).join(relation, JoinType.INNER).get(field)), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ObservacaoConclusaoEntity> nestedLike(String first, String second, String field, String value) {
        if (value == null || value.isBlank()) return Specification.unrestricted();
        return (r, q, c) -> {
            var path = r.join("ordemServico", JoinType.INNER).join(first, JoinType.INNER);
            if (second != null && !second.isBlank()) path = path.join(second, JoinType.INNER);
            return c.like(c.lower(path.get(field)), "%" + value.toLowerCase() + "%");
        };
    }

    private static Specification<ObservacaoConclusaoEntity> equal(String field, Object value) {
        if (value == null) return Specification.unrestricted();
        return (r, q, c) -> c.equal(r.get(field), value);
    }

    private static Specification<ObservacaoConclusaoEntity> enumValue(String field, String value, Class<? extends Enum<?>> type) {
        if (value == null || value.isBlank()) return Specification.unrestricted();
        try {
            Enum<?> parsed = type == CategoriaProblema.class ? CategoriaProblema.fromNome(value)
                    : type == Prioridade.class ? Prioridade.fromNome(value) : Dificuldade.fromNome(value);
            return (r, q, c) -> c.equal(r.join("ordemServico", JoinType.INNER).get(field), parsed);
        } catch (IllegalArgumentException exception) {
            return Specification.unrestricted();
        }
    }
}
