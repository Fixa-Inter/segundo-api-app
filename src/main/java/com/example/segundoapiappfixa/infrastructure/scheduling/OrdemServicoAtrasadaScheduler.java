package com.example.segundoapiappfixa.infrastructure.scheduling;

import com.example.segundoapiappfixa.application.usecase.OrdemServico.VerificarOrdensAtrasadas;
import com.example.segundoapiappfixa.domain.enums.StatusExecucaoJob;
import com.example.segundoapiappfixa.domain.model.ExecucaoJob;
import com.example.segundoapiappfixa.domain.repository.ExecucaoJobRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/** Daily entry point for detecting overdue service orders. Redis prevents duplicate runs across ECS tasks. */
@Component
@RequiredArgsConstructor
public class OrdemServicoAtrasadaScheduler {
    private static final Logger log = LoggerFactory.getLogger(OrdemServicoAtrasadaScheduler.class);
    private static final ZoneId ZONE_ID = ZoneId.of("America/Sao_Paulo");

    private final VerificarOrdensAtrasadas verificarOrdensAtrasadas;
    private final ExecucaoJobRepository execucoes;

    @Scheduled(cron = "${app.jobs.os-atrasadas.cron:0 5 0 * * *}", zone = "America/Sao_Paulo")
    public void executarDiariamente() {
        LocalDate hoje = LocalDate.now(ZONE_ID);
        LocalDateTime iniciadoEm = LocalDateTime.now(ZONE_ID);
        ExecucaoJob execucao = new ExecucaoJob(
                "os-atrasadas:" + hoje,
                "os-atrasadas",
                hoje,
                iniciadoEm,
                null,
                StatusExecucaoJob.EM_EXECUCAO,
                null,
                null
        );

        if (!execucoes.iniciarSeAusente(execucao)) {
            log.info("Job de OS atrasadas ignorado: já existe registro de execução para {}.", hoje);
            return;
        }

        try {
            int atualizadas = verificarOrdensAtrasadas.executar();

            execucao.setFinalizadoEm(LocalDateTime.now(ZONE_ID));
            execucao.setStatus(StatusExecucaoJob.CONCLUIDO);
            execucao.setRegistrosProcessados(atualizadas);
            execucoes.atualizar(execucao);

            log.info("Job diário de OS atrasadas concluído: {} OS(s) atualizada(s).", atualizadas);
        } catch (RuntimeException exception) {
            execucao.setFinalizadoEm(LocalDateTime.now(ZONE_ID));
            execucao.setStatus(StatusExecucaoJob.FALHOU);
            execucao.setErro(exception.getMessage());

            try {
                execucoes.atualizar(execucao);
            } catch (RuntimeException persistError) {
                exception.addSuppressed(persistError);
            }

            log.error("Falha no job diário de OS atrasadas.", exception);
        }
    }
}
