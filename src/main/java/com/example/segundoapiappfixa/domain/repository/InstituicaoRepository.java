package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Instituicao;

import java.util.List;

public interface InstituicaoRepository {

    List<Instituicao> findAll();

}
