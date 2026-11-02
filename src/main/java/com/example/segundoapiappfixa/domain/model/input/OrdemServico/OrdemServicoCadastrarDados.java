package com.example.segundoapiappfixa.domain.model.input.OrdemServico;

import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.Prioridade;
import com.example.segundoapiappfixa.domain.enums.StatusOrdemServico;
import java.time.LocalDate;

public record OrdemServicoCadastrarDados(
        Long problemaId,
        Long usuarioId,
        StatusOrdemServico statusOrdemServico,
        CategoriaProblema categoriaProblema,
        LocalDate dataPrevista,
        Prioridade prioridade
) {}
