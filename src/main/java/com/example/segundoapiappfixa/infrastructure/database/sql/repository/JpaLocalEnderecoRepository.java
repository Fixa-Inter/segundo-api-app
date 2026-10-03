package com.example.segundoapiappfixa.infrastructure.database.sql.repository;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.LocalEnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaLocalEnderecoRepository extends JpaRepository<LocalEnderecoEntity, Long> {

    List<LocalEnderecoEntity> findAllByEndereco_Id(Long enderecoId);
}
