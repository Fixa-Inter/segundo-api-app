package com.example.segundoapiappfixa.application.usecase.OrdemServico;

import com.example.segundoapiappfixa.domain.model.input.OrdemServico.OrdemServicoCadastrarDados;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.enums.TipoNotificacao;
import com.example.segundoapiappfixa.domain.service.NotificacaoSender;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.OrdemServicoRepository;
import com.example.segundoapiappfixa.domain.repository.ProblemaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.application.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.application.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@UseCase
@RequiredArgsConstructor
public class CadastrarOrdemServico {
    private final OrdemServicoRepository ordemServicoRepository;
    private final ProblemaRepository problemaRepository;
    private final UsuarioRepository usuarioRepository;
    private final NotificacaoSender notificacoes;

    @Transactional
    public OrdemServico cadastrar(OrdemServicoCadastrarDados input, Long usuarioAutenticadoId) {

        Usuario gestor = buscarUsuario(usuarioAutenticadoId);
        Usuario responsavel = buscarUsuario(input.usuarioId());

        Problema problema = problemaRepository.findById(input.problemaId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.problema.notFound"));

        if (
                !gestor.getUnidade().getId().equals(responsavel.getUnidade().getId())
                || !gestor.getUnidade().getId().equals(problema.getLocalUnidade().getUnidade().getId())
        ) {
            throw new RegraProblemaException("exception.access.denied");
        }

        if (responsavel.getTipoAcesso() != TipoAcesso.GESTOR
                && responsavel.getTipoAcesso() != TipoAcesso.TECNICO) {
            throw new RegraProblemaException("exception.responsavel.denied");
        }

        if (problema.getStatus() != StatusProblema.APROVADO) {
            throw new RegraProblemaException("exception.problema.notApproved");
        }

        if (ordemServicoRepository.existsByProblemaId(problema.getId())) {
            throw new RegraProblemaException("exception.ordemServico.duplicate");
        }

        OrdemServico ordemServico = new OrdemServico(
                null,
                problema,
                responsavel,
                input.statusOrdemServico(),
                input.categoriaProblema(),
                input.prioridade(),
                LocalDateTime.now(),
                input.dataPrevista().atStartOfDay()
        );

        OrdemServico salva = ordemServicoRepository.save(ordemServico);
        notificacoes.notificarAposPersistencia(
                responsavel.getId(),
                TipoNotificacao.ORDEM_SERVICO.getId(),
                salva.getId(),
                "Uma nova OS foi atribuída a você: "
                + problema.getTitulo()
                + "."
        );

        return salva;
    }

    private Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.notFound"));
    }
}
