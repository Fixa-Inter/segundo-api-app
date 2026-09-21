package com.example.segundoapiappfixa.application.usecase.Tarefa;

import com.example.segundoapiappfixa.adapters.dto.input.Tarefa.TarefaCriarInputDTO;
import com.example.segundoapiappfixa.adapters.mapper.TarefaMapper;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.domain.model.Tarefa;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.OrdemServicoRepository;
import com.example.segundoapiappfixa.domain.repository.StatusOrdemServicoRepository;
import com.example.segundoapiappfixa.domain.repository.TarefaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class CriarTarefa {

    private final TarefaRepository tarefaRepository;
    private final OrdemServicoRepository ordemServicoRepository;
    private final StatusOrdemServicoRepository statusOrdemServicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TarefaMapper tarefaMapper;

    @Transactional
    public List<Tarefa> criarTarefas(
            List<TarefaCriarInputDTO> tarefaCriarInputDTOS,
            Long usuarioId
    ) {

        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        if (usuario == null || usuario.getEndereco() == null) {
            throw new RegraProblemaException("exception.endereco.required");
        }

        List<Tarefa> tarefas = tarefaCriarInputDTOS
                .stream()
                .map(tarefaCriarInputDTO -> {
                    OrdemServico ordemServico = ordemServicoRepository.findById(tarefaCriarInputDTO.ordemServicoId())
                            .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.ordemServico.notFound"));

                    if (!usuario.getEndereco().getId().equals(
                            ordemServico.getProblema().getLocalEndereco().getEndereco().getId())) {
                        throw new RegraProblemaException("exception.access.denied");
                    }

                    return new Tarefa(
                            null,
                            ordemServico,

                            statusOrdemServicoRepository.findById(tarefaCriarInputDTO.statusOrdemServicoId())
                                    .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.statusOrdemServico.notFound")),

                            tarefaCriarInputDTO.titulo(),
                            tarefaCriarInputDTO.descricao(),
                            LocalDateTime.now()
                    );
                })
                .toList();

        return tarefaRepository.saveAll(tarefas);

    }
}
