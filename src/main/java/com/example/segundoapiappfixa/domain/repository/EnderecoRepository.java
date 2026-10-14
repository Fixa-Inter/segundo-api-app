package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Endereco;

import java.util.List;

public interface EnderecoRepository {

    List<Endereco> findAllByInstituicaoId(Long instituicaoId);

}
