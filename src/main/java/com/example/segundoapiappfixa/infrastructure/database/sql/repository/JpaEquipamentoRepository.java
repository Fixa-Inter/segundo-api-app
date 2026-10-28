package com.example.segundoapiappfixa.infrastructure.database.sql.repository;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.EquipamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

@Repository
public interface JpaEquipamentoRepository extends JpaRepository<EquipamentoEntity, Long>, JpaSpecificationExecutor<EquipamentoEntity> {

    @Query("""
            SELECT e.modeloEquipamento.id, COUNT(e.id)
            FROM Equipamento e
            WHERE e.modeloEquipamento.id IN :modeloIds
              AND e.estaAtivo = true
            GROUP BY e.modeloEquipamento.id
            """)
    List<Object[]> countAtivosByModeloIds(@Param("modeloIds") Collection<Long> modeloIds);
}
