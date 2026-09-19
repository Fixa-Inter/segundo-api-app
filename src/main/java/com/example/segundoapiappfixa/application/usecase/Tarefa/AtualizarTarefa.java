package com.example.segundoapiappfixa.application.usecase.Tarefa;

import com.example.segundoapiappfixa.adapters.dto.input.Tarefa.TarefaAtualizarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Tarefa;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.TarefaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
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
        Tarefa tarefa = tarefaRepository.findById(tarefaAtualizarInputDTO.id());

        if (tarefa == null) {
            throw new RegraProblemaException("exception.tarefa.required");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId);

        if (usuario == null || tarefa == null) {
            throw new RegraProblemaException("exception.endereco.required");
        }

        boolean mesmoEndereco = usuario.getEndereco().getId()
                .equals(tarefa.getOrdemServico().getProblema().getLocalEndereco().getEndereco().getId());

        if (!mesmoEndereco) {
            throw new RegraProblemaException("exception.access.denied");
        }

        tarefa.setTitulo(tarefaAtualizarInputDTO.titulo());
        tarefa.setDescricao(tarefaAtualizarInputDTO.descricao());

        return tarefaRepository.update(tarefa.getId(), tarefa);
    }
}
