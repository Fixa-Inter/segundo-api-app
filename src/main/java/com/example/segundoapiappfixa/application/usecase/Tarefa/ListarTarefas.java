package com.example.segundoapiappfixa.application.usecase.Tarefa;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.domain.model.Tarefa;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.OrdemServicoRepository;
import com.example.segundoapiappfixa.domain.repository.TarefaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
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
            Boolean isGestor,
            Long usuarioId
    ) {
        OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.ordemServico.notFound"));

        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        if (!usuario.getEndereco().getId()
                .equals(ordemServico.getProblema().getLocalEndereco().getEndereco().getId()) ||
                (!isGestor && !Objects.equals(ordemServico.getUsuario().getId(), usuarioId))
        ) {
            throw new RegraProblemaException("exception.access.denied");
        }

        return tarefaRepository.findAllByOrdemServicoId(ordemServicoId);
    }

    public Tarefa listarDetalhes(Long tarefaId, boolean isGestor, Long usuarioId) {
        Tarefa tarefa = tarefaRepository.findById(tarefaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.tarefa.notFound"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.notFound"));

        OrdemServico ordemServico = tarefa.getOrdemServico();

        if (!usuario.getEndereco().getId()
                .equals(ordemServico.getProblema().getLocalEndereco().getEndereco().getId()) ||
                (!isGestor && !Objects.equals(ordemServico.getUsuario().getId(), usuarioId))
        ) {
            throw new RegraProblemaException("exception.access.denied");
        }

        return tarefa;
    }
}
