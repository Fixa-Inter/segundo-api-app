package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Ocorrencia;

import java.util.List;
import java.util.Optional;

public interface OcorrenciaRepository {

    List<Ocorrencia> findAllByUsuarioId(Long usuarioId);

    List<Ocorrencia> findAllByLocalEnderecoIds(List<Long> localEnderecoIds);

    Optional<Ocorrencia> findById(Long id);

    Ocorrencia save(Ocorrencia ocorrencia);

    Ocorrencia update(Long id, Ocorrencia ocorrencia);

    Optional<Ocorrencia> deleteById(Long id);
}
