package com.example.segundoapiappfixa.application.usecase.ObservacaoConclusao;

import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosObservacaoConclusaoQueryParam;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.model.ObservacaoConclusao;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.ObservacaoConclusaoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarObservacaoConclusao {
    private final ObservacaoConclusaoRepository repository;
    private final UsuarioRepository usuarioRepository;

    public List<ObservacaoConclusao> listar(Long usuarioId, FiltrosObservacaoConclusaoQueryParam filtros) {
        Usuario usuario = buscarUsuario(usuarioId);

        if (usuario.getEndereco() == null || usuario.getEndereco().getId() == null)
            throw new RegraProblemaException("exception.endereco.required");

        return validarResultado(repository.findAllByEnderecoId(usuario.getEndereco().getId(), filtros));
    }

    public List<ObservacaoConclusao> listarMinhas(Long usuarioId, FiltrosObservacaoConclusaoQueryParam filtros) {
        return validarResultado(repository.findAllByResponsavelId(usuarioId, filtros));
    }

    public ObservacaoConclusao listarDetalhes(Long id, Long usuarioId, boolean gestor) {
        ObservacaoConclusao observacao = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.observacaoConclusao.notFound"));

        validarPermissao(observacao.getOrdemServico(), buscarUsuario(usuarioId), gestor);
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

    private Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.notFound"));
    }

    private List<ObservacaoConclusao> validarResultado(List<ObservacaoConclusao> observacoes) {
        if (observacoes.isEmpty())
            throw new EntidadeNaoEncontradaException("exception.observacaoConclusao.notFound");

        return observacoes;
    }
}
