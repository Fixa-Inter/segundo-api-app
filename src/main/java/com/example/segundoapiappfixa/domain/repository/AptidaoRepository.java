package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Aptidao;

import java.util.List;
import java.util.Optional;

public interface AptidaoRepository {
    List<Aptidao> findByUsuarioId(Long usuarioId);
    Optional<Aptidao> findById(Long id);
    Aptidao save(Aptidao aptidao);
}
