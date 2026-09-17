package com.example.segundoapiappfixa.adapters.dto.output.OrdemServico;

import com.example.segundoapiappfixa.domain.enums.TipoAcesso;

import java.time.LocalDate;

public record OrdemServicoOutputDTO (
        Long id,
        String titulo,
        LocalDate dataCriacao,
        String nomeUsuario,
        TipoAcesso tipoAcesso
) { }
