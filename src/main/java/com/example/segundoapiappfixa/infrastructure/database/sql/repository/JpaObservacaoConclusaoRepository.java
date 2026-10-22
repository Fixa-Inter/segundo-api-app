package com.example.segundoapiappfixa.infrastructure.database.sql.repository;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.ObservacaoConclusaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaObservacaoConclusaoRepository extends JpaRepository<ObservacaoConclusaoEntity, Long>, JpaSpecificationExecutor<ObservacaoConclusaoEntity> {

    boolean existsByOrdemServico_Id(Long ordemServicoId);

}
