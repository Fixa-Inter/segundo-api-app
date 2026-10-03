package com.example.segundoapiappfixa.infrastructure.database.sql.repository;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.CategoriaEquipamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCategoriaEquipamentoRepository extends JpaRepository<CategoriaEquipamentoEntity, Long>, JpaSpecificationExecutor<CategoriaEquipamentoEntity> { }
