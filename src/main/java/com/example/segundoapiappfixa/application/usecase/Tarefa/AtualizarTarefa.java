package com.example.segundoapiappfixa.application.usecase.Tarefa;

import com.example.segundoapiappfixa.adapters.dto.input.Tarefa.TarefaAtualizarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Tarefa;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.TarefaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class AtualizarTarefa {

    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;

    public Tarefa atualizar(
            TarefaAtualizarInputDTO tarefaAtualizarInputDTO,
            Long usuarioId
    ) {
        Tarefa tarefa = tarefaRepository.findById(tarefaAtualizarInputDTO.id())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.tarefa.notFound"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        if (!usuario.getUnidade().getId()
                .equals(tarefa.getOrdemServico().getProblema().getLocalUnidade().getUnidade().getId())) {
            throw new RegraProblemaException("exception.access.denied");
        }

        if (tarefaAtualizarInputDTO.titulo() != null) tarefa.setTitulo(tarefaAtualizarInputDTO.titulo());
        if (tarefaAtualizarInputDTO.descricao() != null) tarefa.setDescricao(tarefaAtualizarInputDTO.descricao());

        if (tarefaAtualizarInputDTO.statusOrdemServico() != null) tarefa.setStatusOrdemServico(tarefaAtualizarInputDTO.statusOrdemServico());

        return tarefaRepository.save(tarefa);
    }
}
