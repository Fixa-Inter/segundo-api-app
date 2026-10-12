package com.example.segundoapiappfixa.application.usecase.ObservacaoConclusao;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.ObservacaoConclusao;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import com.example.segundoapiappfixa.domain.repository.ObservacaoConclusaoRepository;
import com.example.segundoapiappfixa.domain.repository.FotoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeletarObservacaoConclusao {

    private final ObservacaoConclusaoRepository observacaoConclusaoRepository;
    private final UsuarioRepository usuarioRepository;

    public ObservacaoConclusao deletar(
            Long observacaoConclusaoId,
            Long usuarioId,
            Boolean gestor
    ) {
        ObservacaoConclusao observacao = observacaoConclusaoRepository.findById(observacaoConclusaoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "exception.observacaoConclusao.notFound"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.notFound"));

        validarPermissao(observacao.getOrdemServico(), usuario, Boolean.TRUE.equals(gestor));

        observacaoConclusaoRepository.delete(observacaoConclusaoId);

        return observacao;
    }

    public void validarPermissao(OrdemServico ordemServico, Usuario usuario, boolean gestor) {
        boolean mesmoEndereco = usuario.getEndereco().getId().equals(
                ordemServico.getProblema().getLocalEndereco().getEndereco().getId());

        if (gestor && mesmoEndereco) return;

        boolean tecnicoResponsavel = usuario.getId().equals(ordemServico.getUsuario().getId());

        if (!tecnicoResponsavel || !mesmoEndereco)
            throw new RegraProblemaException("exception.access.denied");
    }

}
