package com.example.segundoapiappfixa.application.usecase.Tarefa;

import com.example.segundoapiappfixa.adapters.dto.input.Tarefa.TarefaAtualizarInputDTO;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.repository.TarefaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AtualizarTarefaTest {
    @Mock private TarefaRepository tarefaRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @InjectMocks private AtualizarTarefa atualizarTarefa;

    @Test
    void deveImpedirAtualizacaoForaDoUnidadeDaTarefa() {
        Unidade unidadeTarefa = new Unidade();
        unidadeTarefa.setId(10L);
        Unidade unidadeUsuario = new Unidade();
        unidadeUsuario.setId(20L);

        LocalUnidade local = new LocalUnidade();
        local.setUnidade(unidadeTarefa);
        Problema problema = new Problema();
        problema.setLocalUnidade(local);
        OrdemServico os = new OrdemServico();
        os.setProblema(problema);
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setOrdemServico(os);

        Usuario usuario = new Usuario();
        usuario.setUnidade(unidadeUsuario);
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuario));

        RegraProblemaException exception = assertThrows(RegraProblemaException.class,
                () -> atualizarTarefa.atualizar(new TarefaAtualizarInputDTO(1L, "novo", null, null), 2L));

        assertEquals("exception.access.denied", exception.getMessage());
    }
}
