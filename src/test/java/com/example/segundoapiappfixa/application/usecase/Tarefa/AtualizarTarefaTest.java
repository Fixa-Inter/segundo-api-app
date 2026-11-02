package com.example.segundoapiappfixa.application.usecase.Tarefa;

import com.example.segundoapiappfixa.domain.model.input.Tarefa.TarefaAtualizarDados;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.enums.StatusOrdemServico;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.repository.TarefaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.application.exception.RegraProblemaException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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
                () -> atualizarTarefa.atualizar(new TarefaAtualizarDados(1L, "novo", null, null), 2L, TipoAcesso.TECNICO));

        assertEquals("exception.access.denied", exception.getMessage());
    }

    @Test
    void tecnicoResponsavelAlteraStatusSemAlterarTitulo() {
        Tarefa tarefa = tarefaNaMesmaUnidade(2L);
        when(tarefaRepository.save(any())).thenAnswer(call -> call.getArgument(0));
        Tarefa atualizada = atualizarTarefa.atualizar(
                new TarefaAtualizarDados(1L, "titulo não permitido", null, StatusOrdemServico.CONCLUIDA),
                2L, TipoAcesso.TECNICO);
        assertEquals("original", atualizada.getTitulo());
        assertEquals(StatusOrdemServico.CONCLUIDA, atualizada.getStatusOrdemServico());
    }

    @Test
    void tecnicoNaoResponsavelNaoPodeAtualizar() {
        tarefaNaMesmaUnidade(3L);
        assertThrows(RegraProblemaException.class, () -> atualizarTarefa.atualizar(
                new TarefaAtualizarDados(1L, null, null, StatusOrdemServico.CONCLUIDA), 2L, TipoAcesso.TECNICO));
        verify(tarefaRepository, never()).save(any());
    }

    @Test
    void administradorPodeEditarTarefaDaUnidade() {
        tarefaNaMesmaUnidade(3L);
        when(tarefaRepository.save(any())).thenAnswer(call -> call.getArgument(0));
        Tarefa atualizada = atualizarTarefa.atualizar(new TarefaAtualizarDados(1L, "novo", null, null),
                2L, TipoAcesso.ADMINISTRADOR);
        assertEquals("novo", atualizada.getTitulo());
    }

    @Test
    void solicitanteNaoPodeAtualizarTarefaMesmoSeForResponsavel() {
        assertThrows(RegraProblemaException.class, () -> atualizarTarefa.atualizar(
                new TarefaAtualizarDados(1L, null, null, StatusOrdemServico.CONCLUIDA), 2L, TipoAcesso.SOLICITANTE));
        verify(tarefaRepository, never()).save(any());
    }

    private Tarefa tarefaNaMesmaUnidade(Long responsavelId) {
        Unidade unidade = new Unidade(); unidade.setId(10L);
        LocalUnidade local = new LocalUnidade(); local.setUnidade(unidade);
        Problema problema = new Problema(); problema.setLocalUnidade(local);
        Usuario responsavel = new Usuario(); responsavel.setId(responsavelId);
        OrdemServico os = new OrdemServico(); os.setProblema(problema); os.setUsuario(responsavel);
        Tarefa tarefa = new Tarefa(); tarefa.setId(1L); tarefa.setTitulo("original"); tarefa.setOrdemServico(os);
        Usuario usuario = new Usuario(); usuario.setId(2L); usuario.setUnidade(unidade);
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuario));
        return tarefa;
    }
}
