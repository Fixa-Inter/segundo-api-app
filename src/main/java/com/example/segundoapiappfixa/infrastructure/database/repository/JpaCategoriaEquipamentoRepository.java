package com.example.segundoapiappfixa.infrastructure.database.repository;

import com.example.segundoapiappfixa.infrastructure.database.entity.CategoriaEquipamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCategoriaEquipamentoRepository extends JpaRepository<CategoriaEquipamentoEntity, Long> {

}
