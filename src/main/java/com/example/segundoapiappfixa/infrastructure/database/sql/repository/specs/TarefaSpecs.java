package com.example.segundoapiappfixa.infrastructure.database.sql.repository.specs;

import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.Prioridade;
import com.example.segundoapiappfixa.domain.enums.StatusOrdemServico;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.OrdemServicoEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.TarefaEntity;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public final class TarefaSpecs {
    private TarefaSpecs() {
    }

    public static Specification<TarefaEntity> findByOrdemServicoId(Long v) {
        return (r, q, c) -> c.equal(r.join("ordemServico", JoinType.INNER).get("id"), v);
    }

    public static Specification<TarefaEntity> findByTituloTarefa(String v) {
        return like("titulo", v);
    }

    public static Specification<TarefaEntity> findByDescricao(String v) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        return (r, q, c) -> c.like(c.lower(r.get("descricao")), "%" + v.toLowerCase() + "%");
    }

    public static Specification<TarefaEntity> findByStatusOrdemServico(String v) {
        return enumLike("statusOrdemServico", v, StatusOrdemServico.class);
    }

    public static Specification<TarefaEntity> findByUsuarioResponsavel(String v) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        return (r, q, c) -> c.like(c.lower(r.join("ordemServico", JoinType.INNER).join("usuario", JoinType.INNER).get("nomeCompleto")), "%" + v.toLowerCase() + "%");
    }

    private static Specification<TarefaEntity> like(String f, String v) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        return (r, q, c) -> c.like(c.lower(r.get(f)), "%" + v.toLowerCase() + "%");
    }

    private static Specification<TarefaEntity> enumLike(String f, String v, Class<? extends Enum<?>> enumType) {
        if (v == null || v.isBlank()) return Specification.unrestricted();

        Enum<?> value = enumValue(enumType, v);
        if (value == null) return Specification.unrestricted();
        return (r, q, c) -> c.equal(r.get(f), value);
    }

    private static Specification<TarefaEntity> enumRelationLike(String rel, String field, String v, Class<? extends Enum<?>> enumType) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        Enum<?> value = enumValue(enumType, v);
        if (value == null) return Specification.unrestricted();
        return (r, q, c) -> c.equal(r.join(rel, JoinType.INNER).get(field), value);
    }

    private static Enum<?> enumValue(Class<? extends Enum<?>> enumType, String value) {
        try {
            if (enumType == StatusOrdemServico.class) return StatusOrdemServico.fromNome(value);
            return Enum.valueOf(enumType.asSubclass(Enum.class), value);

        } catch (IllegalArgumentException exception) {
            return null;
        }
    }

}
