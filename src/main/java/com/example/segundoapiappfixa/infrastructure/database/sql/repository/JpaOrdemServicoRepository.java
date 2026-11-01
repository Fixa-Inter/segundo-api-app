package com.example.segundoapiappfixa.infrastructure.database.sql.repository;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.OrdemServicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.segundoapiappfixa.domain.enums.StatusOrdemServico;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface JpaOrdemServicoRepository extends JpaRepository<OrdemServicoEntity, Long>, JpaSpecificationExecutor<OrdemServicoEntity> {
    boolean existsByProblema_Id(Long problemaId);

    List<OrdemServicoEntity> findAllByDataPrevistaBeforeAndStatusOrdemServicoNotIn(
            LocalDateTime inicioDoDiaAtual,
            List<StatusOrdemServico> statusIgnorados
    );
}
