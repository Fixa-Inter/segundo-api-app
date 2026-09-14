package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Foto;

import java.util.List;

public interface FotoRepository {

    List<Foto> findAllByProblemaId(Long problemaId);

    Foto save(Foto foto);
}
