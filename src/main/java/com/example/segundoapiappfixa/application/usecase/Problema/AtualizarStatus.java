package com.example.segundoapiappfixa.application.usecase.Problema;

import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.model.input.Problema.ProblemaAtualizarStatusDados;
import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import com.example.segundoapiappfixa.domain.enums.TipoNotificacao;
import com.example.segundoapiappfixa.domain.service.NotificacaoSender;
import com.example.segundoapiappfixa.application.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.application.exception.RegraProblemaException;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.ProblemaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.application.security.Permissoes;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class AtualizarStatus {

    private final ProblemaRepository problemaRepository;
    private final NotificacaoSender notificacoes;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Problema atualizar(ProblemaAtualizarStatusDados dto, Long usuarioId) {

        Problema problema = problemaRepository.findById(dto.problemaId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.problema.notFound"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.notFound"));

        Long unidadeUsuario = usuario.getUnidade() == null ? null : usuario.getUnidade().getId();
        Long unidadeProblema = problema.getLocalUnidade() == null || problema.getLocalUnidade().getUnidade() == null
                ? null : problema.getLocalUnidade().getUnidade().getId();

        if (unidadeUsuario == null || unidadeProblema == null || !unidadeUsuario.equals(unidadeProblema)) {
            throw new RegraProblemaException("exception.access.denied");
        }

        if (problema.getStatus() != StatusProblema.PENDENTE || dto.statusProblema() == StatusProblema.PENDENTE)
            throw new RegraProblemaException("exception.status.transition");

        boolean possuiMotivoRecusa = dto.motivoRecusa() != null
                && !dto.motivoRecusa().isBlank();

        if (dto.statusProblema() == StatusProblema.REPROVADO && !possuiMotivoRecusa) {
            throw new RegraProblemaException("exception.recusa.required");
        }

        if (dto.statusProblema() != StatusProblema.REPROVADO && possuiMotivoRecusa) {
            throw new RegraProblemaException("exception.recusa.transition");
        }

        problema.setStatus(dto.statusProblema());
        problema.setMotivoRecusa(possuiMotivoRecusa ? dto.motivoRecusa().trim() : null);

        Problema atualizado = problemaRepository.update(problema);
        if (atualizado.getUsuario() != null) {
            notificacoes.notificarAposPersistencia(atualizado.getUsuario().getId(),
                    TipoNotificacao.SOLICITACAO.getId(), atualizado.getId(),
                    "O status da sua solicitação \"" + atualizado.getTitulo() + "\" foi atualizado para "
                            + atualizado.getStatus().getNome() + ".");
        }

        return atualizado;
    }
}
