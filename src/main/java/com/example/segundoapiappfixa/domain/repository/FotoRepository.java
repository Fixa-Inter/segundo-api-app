package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.enums.TipoFoto;
import com.example.segundoapiappfixa.domain.model.Foto;

import java.util.List;

public interface FotoRepository {

    Foto save(Foto foto, TipoFoto tipoFoto);

    void deleteById(Long id);

    void deleteByProblemaId(Long problemaId);

    void deleteByOcorrenciaId(Long ocorrenciaId);

    void deleteByIdAndOcorrenciaId(Long fotoId, Long ocorrenciaId);

    void deleteByOrdemServicoId(Long ordemServicoId);
}
