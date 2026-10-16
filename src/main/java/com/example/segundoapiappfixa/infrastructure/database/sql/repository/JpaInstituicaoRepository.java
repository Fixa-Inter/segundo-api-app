package com.example.segundoapiappfixa.infrastructure.database.sql.repository;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.InstituicaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaInstituicaoRepository extends JpaRepository<InstituicaoEntity, Long> {
    List<InstituicaoEntity> findAllByOrderByNomeAsc();
}
