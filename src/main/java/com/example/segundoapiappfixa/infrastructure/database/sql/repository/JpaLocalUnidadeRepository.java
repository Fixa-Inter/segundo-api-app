package com.example.segundoapiappfixa.infrastructure.database.sql.repository;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.LocalUnidadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaLocalUnidadeRepository extends JpaRepository<LocalUnidadeEntity, Long> {

    List<LocalUnidadeEntity> findAllByUnidade_Id(Long unidadeId);
}
