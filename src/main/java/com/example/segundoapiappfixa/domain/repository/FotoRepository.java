package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.enums.TipoFoto;
import com.example.segundoapiappfixa.domain.model.Foto;

import java.util.List;

public interface FotoRepository {

    Foto save(Foto foto, TipoFoto tipoFoto);

}
