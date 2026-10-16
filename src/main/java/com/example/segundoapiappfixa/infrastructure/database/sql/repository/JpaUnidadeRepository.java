package com.example.segundoapiappfixa.infrastructure.database.sql.repository;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.UnidadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaUnidadeRepository extends JpaRepository<UnidadeEntity, Long> {
    List<UnidadeEntity> findAllByInstituicao_IdOrderByLogradouroAscNumeroAsc(Long instituicaoId);
}
