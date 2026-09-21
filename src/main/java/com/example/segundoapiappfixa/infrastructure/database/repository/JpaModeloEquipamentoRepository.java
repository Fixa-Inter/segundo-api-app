package com.example.segundoapiappfixa.infrastructure.database.repository;

import com.example.segundoapiappfixa.infrastructure.database.entity.ModeloEquipamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaModeloEquipamentoRepository extends JpaRepository<ModeloEquipamentoEntity, Long> {

    List<ModeloEquipamentoEntity> findAllByUsuario_Endereco_Id(Long enderecoId);
}
