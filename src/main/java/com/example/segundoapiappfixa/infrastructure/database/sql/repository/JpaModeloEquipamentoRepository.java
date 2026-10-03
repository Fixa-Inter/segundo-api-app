package com.example.segundoapiappfixa.infrastructure.database.sql.repository;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.ModeloEquipamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaModeloEquipamentoRepository extends JpaRepository<ModeloEquipamentoEntity, Long>, JpaSpecificationExecutor<ModeloEquipamentoEntity> { }
