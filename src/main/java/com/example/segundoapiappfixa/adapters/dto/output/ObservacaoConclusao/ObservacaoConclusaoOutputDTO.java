package com.example.segundoapiappfixa.adapters.dto.output.ObservacaoConclusao;

import com.example.segundoapiappfixa.adapters.dto.output.Foto.FotoOutputDTO;
import com.example.segundoapiappfixa.domain.enums.Dificuldade;
import java.time.LocalDate;
import java.util.List;

public record ObservacaoConclusaoOutputDTO(
        Long id,
        String titulo,
        String descricaoProblema,
        String categoriaProblema,
        String categoriaEquipamento,
        String localEndereco,
        String descricaoLocal,
        String statusProblema,
        String prioridade,
        String statusOrdemServico,
        String nomeResponsavel,
        String dificuldade,
        String observacao,
        LocalDate dataCriacao,
        List<FotoOutputDTO> fotos
) {}
