package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.LocalEndereco;

import java.util.List;

public interface LocalEnderecoRepository {

    List<LocalEndereco> findAllByEnderecoId(Long enderecoId);

    LocalEndereco findById(Long id);
}
