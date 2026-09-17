package com.example.segundoapiappfixa.infrastructure.database.repository;

import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.infrastructure.database.entity.OrdemServicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaOrdemServicoRepository extends JpaRepository<OrdemServicoEntity, Long> {

    List<OrdemServicoEntity> findAllByProblema_IdIn(List<Long> id);

    List<OrdemServicoEntity> findByUsuario_Id(Long usuarioId);

    Long id(Long id);
}
