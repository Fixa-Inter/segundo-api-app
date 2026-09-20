package com.example.segundoapiappfixa.infrastructure.database.repository;

import com.example.segundoapiappfixa.infrastructure.database.entity.OcorrenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaOcorrenciaRepository extends JpaRepository<OcorrenciaEntity, Long> {

    List<OcorrenciaEntity> findAllByUsuario_Id(Long usuarioId);
}
