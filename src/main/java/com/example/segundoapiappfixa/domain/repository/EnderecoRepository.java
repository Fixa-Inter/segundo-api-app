package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Endereco;

import java.util.List;
import java.util.Optional;

public interface EnderecoRepository {

    List<Endereco> findAllByInstituicaoId(Long instituicaoId);

    Optional<Endereco> findById(Long id);

}
