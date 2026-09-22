package com.example.segundoapiappfixa.infrastructure.database.repository;

import com.example.segundoapiappfixa.infrastructure.database.entity.EquipamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaEquipamentoRepository extends JpaRepository<EquipamentoEntity, Long> {

    List<EquipamentoEntity> findAllByModeloEquipamento_Id(Long modeloEquipamentoId);

}
