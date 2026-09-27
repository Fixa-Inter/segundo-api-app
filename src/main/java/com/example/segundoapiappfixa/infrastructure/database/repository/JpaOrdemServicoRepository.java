package com.example.segundoapiappfixa.infrastructure.database.repository;

import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.infrastructure.database.entity.OrdemServicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaOrdemServicoRepository extends JpaRepository<OrdemServicoEntity, Long>, JpaSpecificationExecutor<OrdemServicoEntity> { }
