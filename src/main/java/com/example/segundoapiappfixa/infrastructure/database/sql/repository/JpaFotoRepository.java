package com.example.segundoapiappfixa.infrastructure.database.sql.repository;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.FotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

@Repository
public interface JpaFotoRepository extends JpaRepository<FotoEntity, Long> {

    List<FotoEntity> findByProblema_Id(Long problemaId);

    List<FotoEntity> findByObservacaoConclusao_Id(Long observacaoConclusaoId);

    @Modifying
    void deleteByObservacaoConclusao_Id(Long observacaoConclusaoId);

}
