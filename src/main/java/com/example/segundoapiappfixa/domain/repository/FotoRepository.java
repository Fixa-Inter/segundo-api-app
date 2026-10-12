package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.enums.TipoFoto;
import com.example.segundoapiappfixa.domain.model.Foto;

import java.util.List;

public interface FotoRepository {

    List<Foto> findAllByProblemaId(Long problemaId);

    List<Foto> findaAllByObservacaoConclusaoId(Long observacaoConclusao);

    void deleteAllByObservacaoConclusaoId(Long observacaoConclusaoId);

    Foto save(Foto foto, TipoFoto tipoFoto);
}
