package com.example.segundoapiappfixa.infrastructure.database.sql.repository.specs;

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
        if (v == null || v.isBlank()) return Specification.unrestricted();
        return (r, q, c) -> c.like(c.lower(r.join("statusOrdemServico", JoinType.INNER).get("nome")), "%" + v.toLowerCase() + "%");
    }

    public static Specification<TarefaEntity> findByUsuarioResponsavel(String v) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        return (r, q, c) -> c.like(c.lower(r.join("ordemServico", JoinType.INNER).join("usuario", JoinType.INNER).get("nomeCompleto")), "%" + v.toLowerCase() + "%");
    }

    private static Specification<TarefaEntity> like(String f, String v) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        return (r, q, c) -> c.like(c.lower(r.get(f)), "%" + v.toLowerCase() + "%");
    }
}
