package com.example.segundoapiappfixa.infrastructure.database.repository.specs;

import com.example.segundoapiappfixa.infrastructure.database.entity.*;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public final class EventoSpecs {

    public static Specification<EventoEntity> findByUsuarioId(Long id) {
        return (r, q, cb) ->
                cb.equal(r.join("usuario", JoinType.INNER).get("id"), id);
    }

    public static Specification<EventoEntity> findByTitulo(String v) {
        return like("titulo", v);
    }

    public static Specification<EventoEntity> findByDescricao(String v) {
        return like("descricao", v);
    }

    public static Specification<EventoEntity> findByDescricaoLocal(String v) {
        return like("descricaoLocal", v);
    }

    public static Specification<EventoEntity> findByObservacao(String v) {
        return like("observacao", v);
    }

    public static Specification<EventoEntity> findByLocalEndereco(String v) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        return (r, q, cb) -> cb.like(
                cb.lower(r.join("localEndereco", JoinType.INNER).get("nome")),
                "%" + v.toLowerCase() + "%"
        );
    }

    public static Specification<EventoEntity> findByDataHoraInicio(LocalDateTime min) {
        if (min == null) return Specification.unrestricted();
        return (r, q, cb) -> cb.greaterThanOrEqualTo(r.get("dataHoraInicio"), min);
    }

    public static Specification<EventoEntity> findByDataHoraFim(LocalDateTime max) {
        if (max == null) return Specification.unrestricted();
        return (r, q, cb) -> cb.lessThanOrEqualTo(r.get("dataHoraInicio"), max);
    }

    public static Specification<EventoEntity> findByUsuario(String v) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        return (r, q, cb) -> cb.like(cb.lower(r.join("usuario", JoinType.INNER).get("nomeCompleto")), "%" + v.toLowerCase() + "%");
    }

    private static Specification<EventoEntity> like(String campo, String v) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        return (r, q, cb) -> cb.like(cb.lower(r.get(campo)), "%" + v.toLowerCase() + "%");
    }
}
