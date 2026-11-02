package com.example.segundoapiappfixa.infrastructure.scheduling;

import com.example.segundoapiappfixa.application.usecase.OrdemServico.VerificarOrdensAtrasadas;
import com.example.segundoapiappfixa.domain.enums.StatusExecucaoJob;
import com.example.segundoapiappfixa.domain.enums.TipoNotificacao;
import com.example.segundoapiappfixa.domain.model.ExecucaoJob;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.domain.repository.ExecucaoJobRepository;
import com.example.segundoapiappfixa.domain.repository.OrdemServicoRepository;
import com.example.segundoapiappfixa.infrastructure.external.notification.NotificacaoInfrastructureService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.function.IntSupplier;

/** Sends the daily overdue-order reminders; Redis prevents duplicate runs across ECS tasks. */
@Component
@RequiredArgsConstructor
public class OrdemServicoAtrasadaScheduler {
    private static final Logger log = LoggerFactory.getLogger(OrdemServicoAtrasadaScheduler.class);
    private static final ZoneId ZONE_ID = ZoneId.of("America/Sao_Paulo");

    private final VerificarOrdensAtrasadas verificarOrdensAtrasadas;
    private final ExecucaoJobRepository execucoes;
    private final OrdemServicoRepository ordensServico;
    private final NotificacaoInfrastructureService notificacoes;

    @Scheduled(cron = "${app.jobs.os-atrasadas.cron:0 0 9 * * *}", zone = "America/Sao_Paulo")
    public void enviarPrimeiraNotificacao() {
        executarJob("09h", this::atualizarENotificarOrdensAtrasadas);
    }

    @Scheduled(cron = "${app.jobs.os-atrasadas.segundo-cron:0 30 1 * * *}", zone = "America/Sao_Paulo")
    public void enviarSegundaNotificacao() {
        executarJob("15h", this::atualizarENotificarOrdensAtrasadas);
    }

    private int notificarOrdensVencidasNaoConcluidas() {
        LocalDateTime inicioDoDiaAtual = LocalDate.now(ZONE_ID).atStartOfDay();
        List<OrdemServico> atrasadas = ordensServico.findVencidasNaoConcluidas(inicioDoDiaAtual);
        int notificacoesEnfileiradas = 0;

        log.info("Foram encontradas {} OS(s) vencidas e não concluídas para notificação.", atrasadas.size());

        for (OrdemServico ordem : atrasadas) {
            if (ordem.getUsuario() == null || ordem.getUsuario().getId() == null) {
                log.warn("Notificação de atraso ignorada para OS {}: não há usuário responsável associado.",
                        ordem.getId());
                continue;
            }

            String tituloProblema = ordem.getProblema() == null ? "OS " + ordem.getId()
                    : ordem.getProblema().getTitulo();
            Long usuarioId = ordem.getUsuario().getId();

            notificacoes.notificarAposPersistencia(
                    usuarioId,
                    TipoNotificacao.ORDEM_SERVICO.getId(),
                    ordem.getId(),
                    "A OS \"" + tituloProblema + "\" está atrasada."
            );
            notificacoesEnfileiradas++;
            log.info("Notificação de atraso da OS {} enfileirada para o usuário {}.", ordem.getId(), usuarioId);
        }

        log.info("OS vencidas e não concluídas: {} encontrada(s), {} notificação(ões) enfileirada(s).",
                atrasadas.size(), notificacoesEnfileiradas);
        return notificacoesEnfileiradas;
    }

    private int atualizarENotificarOrdensAtrasadas() {
        verificarOrdensAtrasadas.executar();
        return notificarOrdensVencidasNaoConcluidas();
    }

    private void executarJob(String horario, IntSupplier rotina) {
        LocalDate hoje = LocalDate.now(ZONE_ID);
        LocalDateTime iniciadoEm = LocalDateTime.now(ZONE_ID);
        ExecucaoJob execucao = new ExecucaoJob(
                "os-atrasadas:" + hoje + ":" + horario,
                "os-atrasadas-" + horario,
                hoje,
                iniciadoEm,
                null,
                StatusExecucaoJob.EM_EXECUCAO,
                null,
                null
        );

        if (!execucoes.iniciarSeAusente(execucao)) {
            log.info("Job de notificação de OS atrasadas ignorado: execução {} já existe.", horario);
            return;
        }

        try {
            int registrosProcessados = rotina.getAsInt();

            execucao.setFinalizadoEm(LocalDateTime.now(ZONE_ID));
            execucao.setStatus(StatusExecucaoJob.CONCLUIDO);
            execucao.setRegistrosProcessados(registrosProcessados);
            execucoes.atualizar(execucao);

            log.info("Job de OS atrasadas das {} concluído: {} notificação(ões) enfileirada(s).",
                    horario, registrosProcessados);
        } catch (RuntimeException exception) {
            execucao.setFinalizadoEm(LocalDateTime.now(ZONE_ID));
            execucao.setStatus(StatusExecucaoJob.FALHOU);
            execucao.setErro(exception.getMessage());

            try {
                execucoes.atualizar(execucao);
            } catch (RuntimeException persistError) {
                exception.addSuppressed(persistError);
            }

            log.error("Falha no job de OS atrasadas das {}.", horario, exception);
        }
    }
}
