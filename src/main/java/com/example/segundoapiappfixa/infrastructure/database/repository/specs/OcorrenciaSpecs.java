package com.example.segundoapiappfixa.infrastructure.database.repository.specs;

import com.example.segundoapiappfixa.infrastructure.database.entity.*;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.time.LocalDateTime;

public final class OcorrenciaSpecs {
    private OcorrenciaSpecs() {
    }

    public static Specification<OcorrenciaEntity> findByUsuarioId(Long v) {
        return (r, q, c) -> c.equal(r.join("usuario", JoinType.INNER).get("id"), v);
    }

    public static Specification<OcorrenciaEntity> findByLocalEnderecoIds(List<Long> v) {
        return (r, q, c) -> r.join("localEndereco", JoinType.INNER).get("id").in(v);
    }

    public static Specification<OcorrenciaEntity> findByTitulo(String v) {
        return like("titulo", v);
    }

    public static Specification<OcorrenciaEntity> findByDescricaoOcorrencia(String v) {
        return like("descricaoOcorrencia", v);
    }

    public static Specification<OcorrenciaEntity> findByDescricaoLocal(String v) {
        return like("descricaoLocal", v);
    }

    public static Specification<OcorrenciaEntity> findByCategoriaProblema(String v) {
        return enumLike("categoriaProblema", v);
    }

    public static Specification<OcorrenciaEntity> findByPrioridade(String v) {
        return enumLike("prioridade", v);
    }

    public static Specification<OcorrenciaEntity> findByLocalEndereco(String v) {
        return relationLike("localEndereco", "nome", v);
    }

    public static Specification<OcorrenciaEntity> findByEquipamentoCodigo(String v) {
        return relationLike("equipamento", "codigo", v);
    }

    public static Specification<OcorrenciaEntity> findByNomeUsuario(String v) {
        return relationLike("usuario", "nomeCompleto", v);
    }

    public static Specification<OcorrenciaEntity> findByTipoAcesso(String v) {
        return relationLike("usuario", "tipoAcesso", v);
    }

    public static Specification<OcorrenciaEntity> findByDataCriacaoMin(LocalDateTime v) {
        if (v == null) return Specification.unrestricted();
        return (r, q, c) -> c.greaterThanOrEqualTo(r.get("dataCriacao"), v);
    }

    public static Specification<OcorrenciaEntity> findByDataCriacaoMax(LocalDateTime v) {
        if (v == null) return Specification.unrestricted();
        return (r, q, c) -> c.lessThanOrEqualTo(r.get("dataCriacao"), v);
    }

    private static Specification<OcorrenciaEntity> like(String f, String v) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        return (r, q, c) -> c.like(c.lower(r.get(f)), "%" + v.toLowerCase() + "%");
    }

    private static Specification<OcorrenciaEntity> enumLike(String f, String v) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        return (r, q, c) -> c.equal(r.get(f), v);
    }

    private static Specification<OcorrenciaEntity> relationLike(String rel, String f, String v) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        return (r, q, c) -> c.like(c.lower(r.join(rel, JoinType.INNER).get(f)), "%" + v.toLowerCase() + "%");
    }
}
