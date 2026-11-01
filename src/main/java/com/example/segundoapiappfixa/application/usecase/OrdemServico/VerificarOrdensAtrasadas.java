package com.example.segundoapiappfixa.application.usecase.OrdemServico;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.StatusOrdemServico;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.domain.repository.OrdemServicoRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class VerificarOrdensAtrasadas {
    public static final ZoneId ZONE_ID = ZoneId.of("America/Sao_Paulo");

    private final OrdemServicoRepository repository;

    public int executar() {
        LocalDateTime inicioDoDiaAtual = LocalDate.now(ZONE_ID).atStartOfDay();
        List<OrdemServico> vencidas = repository.findAtrasadas(inicioDoDiaAtual);

        for (OrdemServico ordemServico : vencidas) {
            ordemServico.setStatusOrdemServico(StatusOrdemServico.ATRASADA);
            repository.save(ordemServico);
        }

        return vencidas.size();
    }
}
