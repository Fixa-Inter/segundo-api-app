package com.example.segundoapiappfixa.domain.model.input.ObservacaoConclusao;

import com.example.segundoapiappfixa.domain.enums.Dificuldade;
import java.util.List;

public record ObservacaoConclusaoAtualizarDados(
        Long id,
        Dificuldade dificuldade,
        String observacao,
        List< String> fotos
) {}
