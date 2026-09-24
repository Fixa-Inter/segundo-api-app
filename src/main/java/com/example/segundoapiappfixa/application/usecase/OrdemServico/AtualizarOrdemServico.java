package com.example.segundoapiappfixa.application.usecase.OrdemServico;

import com.example.segundoapiappfixa.adapters.dto.input.OrdemServico.OrdemServicoAtualizarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.domain.model.StatusOrdemServico;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.OrdemServicoRepository;
import com.example.segundoapiappfixa.domain.repository.StatusOrdemServicoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class AtualizarOrdemServico {
    private final OrdemServicoRepository ordemServicoRepository;
    private final StatusOrdemServicoRepository statusRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public OrdemServico atualizar(OrdemServicoAtualizarInputDTO input, Long usuarioAutenticadoId, boolean isGestor) {
        OrdemServico ordemServico = ordemServicoRepository.findById(input.id())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.ordemServico.notFound"));

        Usuario gestor = usuarioRepository.findById(usuarioAutenticadoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.notFound"));

        if (!isGestor) {
            validarAtualizacaoDoTecnico(input, ordemServico, usuarioAutenticadoId);
        }

        if (!gestor.getEndereco().getId().equals(
                ordemServico.getProblema().getLocalEndereco().getEndereco().getId())) {
            throw new RegraProblemaException("exception.access.denied");
        }

        if (input.usuarioId() != null) {
            Usuario responsavel = usuarioRepository.findById(input.usuarioId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.notFound"));

            if (responsavel.getEndereco() == null || !gestor.getEndereco().getId().equals(responsavel.getEndereco().getId())) {
                throw new RegraProblemaException("exception.access.denied");
            }

            ordemServico.setUsuario(responsavel);
        }

        if (input.statusOrdemServicoId() != null) {
            StatusOrdemServico status = statusRepository.findById(input.statusOrdemServicoId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.statusOrdemServico.notFound"));

            ordemServico.setStatusOrdemServico(status);
        }

        if (input.categoriaProblema() != null) ordemServico.setCategoriaProblema(input.categoriaProblema());
        if (input.prioridade() != null) ordemServico.setPrioridade(input.prioridade());
        if (input.dataPrevista() != null) ordemServico.setDataPrevista(input.dataPrevista().atStartOfDay());

        return ordemServicoRepository.save(ordemServico);
    }

    private void validarAtualizacaoDoTecnico(
            OrdemServicoAtualizarInputDTO input,
            OrdemServico ordemServico,
            Long usuarioAutenticadoId
    ) {
        boolean atualizaApenasStatus = input.statusOrdemServicoId() != null
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
