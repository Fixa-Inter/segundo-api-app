package com.example.segundoapiappfixa.infrastructure.database.sql.repository.specs;

import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.UsuarioEntity;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public final class UsuarioSpecs {

    public static Specification<UsuarioEntity> findByEstaAtivo() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("estaAtivo"));
    }

    public static Specification<UsuarioEntity> findByEnderecoId(Long v) {
        return (r, q, c) -> c.equal(r.join("endereco", JoinType.INNER).get("id"), v);
    }

    public static Specification<UsuarioEntity> findByTipoAcesso(TipoAcesso v) {
        return (r, q, c) -> c.equal(r.get("tipoAcesso"), v);
    }

    public static Specification<UsuarioEntity> findByNomeCompleto(String v) {
        return like("nomeCompleto", v);
    }

    public static Specification<UsuarioEntity> findByEmail(String v) {
        return like("email", v);
    }

    public static Specification<UsuarioEntity> findByCargo(String v) {
        return like("cargo", v);
    }

    public static Specification<UsuarioEntity> findByDataNascimento(LocalDate dataNascimento) {
        return (r, q, c) -> c.equal(r.get("dataNascimento"), dataNascimento);
    }

    public static Specification<UsuarioEntity> findByCnpjEndereco(String v) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        return (r, q, c) -> c.like(c.lower(r.join("endereco", JoinType.INNER).get("cnpj")), "%" + v.toLowerCase() + "%");
    }

    public static Specification<UsuarioEntity> findByEstaAtivo(Boolean v) {
        if (v == null) return Specification.unrestricted();
        return (r, q, c) -> c.equal(r.get("estaAtivo"), v);
    }

    private static Specification<UsuarioEntity> like(String f, String v) {
        if (v == null || v.isBlank()) return Specification.unrestricted();
        return (r, q, c) -> c.like(c.lower(r.get(f)), "%" + v.toLowerCase() + "%");
    }
}
