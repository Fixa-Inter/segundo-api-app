package com.example.segundoapiappfixa.infrastructure.database.repository;

import com.example.segundoapiappfixa.infrastructure.database.entity.FotoEntity;
import com.example.segundoapiappfixa.infrastructure.database.entity.ProblemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaFotoRepository extends JpaRepository<FotoEntity, Long> {

    List<FotoEntity> findByProblema_Id(Long problemaId);

}
