package com.example.segundoapiappfixa.application.usecase.Tarefa;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.domain.model.Tarefa;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.TarefaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.OrdemServicoNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import com.example.segundoapiappfixa.infrastructure.exception.TarefaNaoEncontradaException;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeletarTarefa {

    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;

    public Tarefa deletarTarefa(
            Long tarefaId,
            Boolean isGestor,
            Long usuarioId
    ) {
        Tarefa tarefa = tarefaRepository.findById(tarefaId).orElse(null);

        if (!isGestor) throw new RegraProblemaException("exception.gestor.required");

        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        if (
                usuario == null ||
                        tarefa == null ||
                        !usuario.getEndereco().getId()
                                .equals(tarefa.getOrdemServico().getProblema().getLocalEndereco().getEndereco().getId())

        ) {
            throw new RegraProblemaException("exception.access.denied");
        }

        return tarefaRepository.deleteById(tarefaId)
                .orElseThrow(TarefaNaoEncontradaException::new);
    }
}
