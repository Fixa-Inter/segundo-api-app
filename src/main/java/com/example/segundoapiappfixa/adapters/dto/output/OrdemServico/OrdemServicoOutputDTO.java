package com.example.segundoapiappfixa.adapters.dto.output.OrdemServico;

import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrdemServicoOutputDTO (
        Long id,
        String titulo,
        LocalDate dataCriacao,
        String nomeUsuario,
        TipoAcesso tipoAcesso
) { }
