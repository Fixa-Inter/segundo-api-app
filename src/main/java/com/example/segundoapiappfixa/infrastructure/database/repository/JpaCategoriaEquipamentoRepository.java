package com.example.segundoapiappfixa.infrastructure.database.repository;

import com.example.segundoapiappfixa.infrastructure.database.entity.CategoriaEquipamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaCategoriaEquipamentoRepository extends JpaRepository<CategoriaEquipamentoEntity, Long>, JpaSpecificationExecutor<CategoriaEquipamentoEntity> { }
