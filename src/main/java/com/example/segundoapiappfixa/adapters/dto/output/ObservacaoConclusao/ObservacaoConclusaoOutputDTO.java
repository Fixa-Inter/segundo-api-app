package com.example.segundoapiappfixa.adapters.dto.output.ObservacaoConclusao;

import com.example.segundoapiappfixa.adapters.dto.output.Foto.FotoOutputDTO;
import com.example.segundoapiappfixa.domain.enums.Dificuldade;
import java.time.LocalDateTime;
import java.util.List;

public record ObservacaoConclusaoOutputDTO(
        Long id,
        String titulo,
        String descricaoProblema,
        String categoriaProblema,
        String categoriaEquipamento,
        String localUnidade,
        String descricaoLocal,
        String statusProblema,
        String prioridade,
        String statusOrdemServico,
        String nomeResponsavel,
        String dificuldade,
        String observacao,
        LocalDateTime dataCriacao,
        List<FotoOutputDTO> fotos
) {}
