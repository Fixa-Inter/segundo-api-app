package com.example.segundoapiappfixa.infrastructure.database.repository;

import com.example.segundoapiappfixa.infrastructure.database.entity.ProblemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaProblemaRepositiory extends JpaRepository<ProblemaEntity, Long> {

    List<ProblemaEntity> findAllByUsuario_Id(Long usuarioId);

    List<ProblemaEntity> findAllByLocalEndereco_IdIn(List<Long> localEnderecoIds);
}
