package com.example.segundoapiappfixa.application.usecase.Tarefa;

import com.example.segundoapiappfixa.application.security.Permissoes;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.domain.model.Tarefa;
import com.example.segundoapiappfixa.domain.filter.FiltrosTarefa;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.OrdemServicoRepository;
import com.example.segundoapiappfixa.domain.repository.TarefaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.application.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.application.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@UseCase
@RequiredArgsConstructor
public class ListarTarefas {

    private final TarefaRepository tarefaRepository;
    private final OrdemServicoRepository ordemServicoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Tarefa> listar(
            Long ordemServicoId,
            TipoAcesso permissao,
            Long usuarioId,
            FiltrosTarefa filtros
    ) {
        Permissoes.validarOperacional(permissao);
        OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.ordemServico.notFound"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        if (!usuario.getUnidade().getId()
                .equals(ordemServico.getProblema().getLocalUnidade().getUnidade().getId()) ||
                (!Permissoes.gestor(permissao) && !Objects.equals(ordemServico.getUsuario().getId(), usuarioId))
        ) {
            throw new RegraProblemaException("exception.access.denied");
        }

        return tarefaRepository.findAllByOrdemServicoId(ordemServicoId, filtros);
    }

    public Tarefa listarDetalhes(Long tarefaId, TipoAcesso permissao, Long usuarioId) {
        Permissoes.validarOperacional(permissao);
        Tarefa tarefa = tarefaRepository.findById(tarefaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.tarefa.notFound"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.notFound"));

        OrdemServico ordemServico = tarefa.getOrdemServico();

        if (!usuario.getUnidade().getId()
                .equals(ordemServico.getProblema().getLocalUnidade().getUnidade().getId()) ||
                (!Permissoes.gestor(permissao) && !Objects.equals(ordemServico.getUsuario().getId(), usuarioId))
        ) {
            throw new RegraProblemaException("exception.access.denied");
        }

        return tarefa;
    }
}
