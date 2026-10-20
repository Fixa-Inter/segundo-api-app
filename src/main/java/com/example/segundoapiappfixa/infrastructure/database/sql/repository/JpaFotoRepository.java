package com.example.segundoapiappfixa.infrastructure.database.sql.repository;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.FotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaFotoRepository extends JpaRepository<FotoEntity, Long> {

    Optional<FotoEntity> findByUsuario_Id(Long usuarioId);

    List<FotoEntity> findByProblema_Id(Long problemaId);

    void deleteByProblema_Id(Long problemaId);

    List<FotoEntity> findByObservacaoConclusao_Id(Long observacaoConclusaoId);

    @Modifying
    void deleteByObservacaoConclusao_Id(Long observacaoConclusaoId);

}
