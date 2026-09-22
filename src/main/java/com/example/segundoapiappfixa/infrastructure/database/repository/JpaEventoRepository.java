package com.example.segundoapiappfixa.infrastructure.database.repository;

import com.example.segundoapiappfixa.infrastructure.database.entity.EventoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaEventoRepository extends JpaRepository<EventoEntity, Long> {

    List<EventoEntity> findAllByUsuario_Id(Long usuarioId);

}
