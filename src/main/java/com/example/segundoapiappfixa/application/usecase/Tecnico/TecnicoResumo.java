package com.example.segundoapiappfixa.application.usecase.Tecnico;

import com.example.segundoapiappfixa.domain.model.Aptidao;
import com.example.segundoapiappfixa.domain.model.Usuario;

public record TecnicoResumo(
        Usuario tecnico,
        Aptidao competenciaVencedora,
        Long tarefasEmAndamento
) {
}
