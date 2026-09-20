package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.LocalEndereco;

import java.util.List;
import java.util.Optional;

public interface LocalEnderecoRepository {

    List<LocalEndereco> findAllByEnderecoId(Long enderecoId);

    Optional<LocalEndereco> findById(Long id);
}
