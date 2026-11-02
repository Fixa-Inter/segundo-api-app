package com.example.segundoapiappfixa.application.usecase.OrdemServico;

import com.example.segundoapiappfixa.domain.model.input.OrdemServico.OrdemServicoAtualizarDados;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.enums.TipoNotificacao;
import com.example.segundoapiappfixa.domain.service.NotificacaoSender;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.OrdemServicoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.application.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.application.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;

@UseCase
@RequiredArgsConstructor
public class AtualizarOrdemServico {
    private final OrdemServicoRepository ordemServicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final NotificacaoSender notificacoes;

    @Transactional
    public OrdemServico atualizar(OrdemServicoAtualizarDados input, Long usuarioAutenticadoId, boolean isGestor) {
        OrdemServico ordemServico = ordemServicoRepository.findById(input.id())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.ordemServico.notFound"));

        Long responsavelAnteriorId = ordemServico.getUsuario() == null ? null : ordemServico.getUsuario().getId();

        Usuario gestor = usuarioRepository.findById(usuarioAutenticadoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.notFound"));

        if (!isGestor) {
            validarAtualizacaoDoTecnico(input, ordemServico, usuarioAutenticadoId);
        }

        if (!gestor.getUnidade().getId().equals(
                ordemServico.getProblema().getLocalUnidade().getUnidade().getId())) {
            throw new RegraProblemaException("exception.access.denied");
        }

        if (input.usuarioId() != null) {
            Usuario responsavel = usuarioRepository.findById(input.usuarioId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.notFound"));

            if (responsavel.getTipoAcesso() != TipoAcesso.GESTOR
                    && responsavel.getTipoAcesso() != TipoAcesso.TECNICO) {
                throw new RegraProblemaException("exception.responsavel.denied");
            }

            if (responsavel.getUnidade() == null || !gestor.getUnidade().getId().equals(responsavel.getUnidade().getId())) {
                throw new RegraProblemaException("exception.access.denied");
            }

            ordemServico.setUsuario(responsavel);
        }

        if (input.statusOrdemServico() != null) ordemServico.setStatusOrdemServico(input.statusOrdemServico());

        if (input.categoriaProblema() != null) ordemServico.setCategoriaProblema(input.categoriaProblema());
        if (input.prioridade() != null) ordemServico.setPrioridade(input.prioridade());
        if (input.dataPrevista() != null) ordemServico.setDataPrevista(input.dataPrevista().atStartOfDay());

        OrdemServico salva = ordemServicoRepository.save(ordemServico);
        Long responsavelAtualId = salva.getUsuario() == null ? null : salva.getUsuario().getId();

        if (responsavelAtualId != null && !Objects.equals(responsavelAnteriorId, responsavelAtualId)) {
            notificacoes.notificarAposPersistencia(
                    responsavelAtualId,
                    TipoNotificacao.ORDEM_SERVICO.getId(),
                    salva.getId(),
                    "Uma nova OS foi atribuída a você: "
                    + ordemServico.getProblema().getTitulo()
                    + "."
            );
        }

        return salva;
    }

    private void validarAtualizacaoDoTecnico(
            OrdemServicoAtualizarDados input,
            OrdemServico ordemServico,
            Long usuarioAutenticadoId
    ) {
        boolean atualizaApenasStatus = input.statusOrdemServico() != null
                && input.usuarioId() == null
                && input.categoriaProblema() == null
                && input.dataPrevista() == null
                && input.prioridade() == null;

        boolean tecnicoVinculado = ordemServico.getUsuario() != null
                && usuarioAutenticadoId.equals(ordemServico.getUsuario().getId());

        if (!atualizaApenasStatus || !tecnicoVinculado) {
            throw new RegraProblemaException("exception.access.denied");
        }
    }
}
