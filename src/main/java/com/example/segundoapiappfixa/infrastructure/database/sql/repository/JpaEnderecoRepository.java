package com.example.segundoapiappfixa.infrastructure.database.sql.repository;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaEnderecoRepository extends JpaRepository<EnderecoEntity, Long> {
    List<EnderecoEntity> findAllByInstituicao_IdOrderByLogradouroAscNumeroAsc(Long instituicaoId);
}
