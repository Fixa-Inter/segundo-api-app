package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import com.example.segundoapiappfixa.domain.model.Problema;

import java.util.List;
import java.util.Optional;

public interface ProblemaRepository {

    List<Problema> findAllByUsuarioId(Long usuarioId);

    List<Problema> findAllByLocalEnderecoIds(List<Long> localEnderecoIds);

    Optional<Problema> findById(Long problemaId);

    Problema save(Problema problema);

    Problema updateStatus(Problema problema, StatusProblema statusProblema, String motivoRecusa);

}
