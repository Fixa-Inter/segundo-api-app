package com.example.segundoapiappfixa.application.usecase.Problema;

import com.example.segundoapiappfixa.application.exception.RegraProblemaException;
import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import com.example.segundoapiappfixa.domain.model.LocalUnidade;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.domain.model.Unidade;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.model.input.Problema.ProblemaAtualizarStatusDados;
import com.example.segundoapiappfixa.domain.repository.ProblemaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.domain.service.NotificacaoSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarStatusTest {
    @Mock private ProblemaRepository problemaRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private NotificacaoSender notificacoes;
    @InjectMocks private AtualizarStatus atualizarStatus;

    @Test
    void usuarioDaMesmaUnidadePodeAlterarStatus() {
        Usuario gestor = usuario(10L);
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(gestor));
        when(problemaRepository.update(any())).thenAnswer(call -> call.getArgument(0));
        Problema problema = problemaPendente(10L);
        when(problemaRepository.findById(1L)).thenReturn(Optional.of(problema));
        Problema resultado = atualizarStatus.atualizar(dados(StatusProblema.APROVADO, null), 2L);
        assertEquals(StatusProblema.APROVADO, resultado.getStatus());
        verify(problemaRepository).update(any());
    }

    @Test
    void gestorDeOutraUnidadeNaoAlteraNemNotifica() {
        when(problemaRepository.findById(1L)).thenReturn(Optional.of(problemaPendente(10L)));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuario(20L)));
        RegraProblemaException erro = assertThrows(RegraProblemaException.class,
                () -> atualizarStatus.atualizar(dados(StatusProblema.APROVADO, null), 2L));
        assertEquals("exception.access.denied", erro.getMessage());
        verify(problemaRepository, never()).update(any());
        verifyNoInteractions(notificacoes);
    }

    @Test
    void ausenciaDeUnidadeNaoPermiteAlteracao() {
        when(problemaRepository.findById(1L)).thenReturn(Optional.of(problemaPendente(10L)));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(new Usuario()));
        assertThrows(RegraProblemaException.class,
                () -> atualizarStatus.atualizar(dados(StatusProblema.APROVADO, null), 2L));
        verify(problemaRepository, never()).update(any());
        verifyNoInteractions(notificacoes);
    }

    @Test
    void ausenciaDeUnidadeDaSolicitacaoNaoPermiteAlteracao() {
        Problema problema = problemaPendente(10L);
        problema.setLocalUnidade(null);
        when(problemaRepository.findById(1L)).thenReturn(Optional.of(problema));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuario(10L)));
        assertThrows(RegraProblemaException.class,
                () -> atualizarStatus.atualizar(dados(StatusProblema.APROVADO, null), 2L));
        verify(problemaRepository, never()).update(any());
        verifyNoInteractions(notificacoes);
    }

    @Test
    void gestorDaUnidadePodeReprovarComMotivo() {
        permitirAcesso();
        when(problemaRepository.update(any())).thenAnswer(call -> call.getArgument(0));
        Problema resultado = atualizarStatus.atualizar(dados(StatusProblema.REPROVADO, "  motivo  "), 2L);
        assertEquals(StatusProblema.REPROVADO, resultado.getStatus());
        assertEquals("motivo", resultado.getMotivoRecusa());
    }

    @Test
    void deveExigirMotivoAoReprovarProblema() {
        permitirAcesso();
        RegraProblemaException erro = assertThrows(RegraProblemaException.class,
                () -> atualizarStatus.atualizar(dados(StatusProblema.REPROVADO, "   "), 2L));
        assertEquals("exception.recusa.required", erro.getMessage());
    }

    @Test
    void deveImpedirMotivoQuandoProblemaForAprovado() {
        permitirAcesso();
        RegraProblemaException erro = assertThrows(RegraProblemaException.class,
                () -> atualizarStatus.atualizar(dados(StatusProblema.APROVADO, "motivo indevido"), 2L));
        assertEquals("exception.recusa.transition", erro.getMessage());
    }

    private void permitirAcesso() {
        when(problemaRepository.findById(1L)).thenReturn(Optional.of(problemaPendente(10L)));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuario(10L)));
    }

    private ProblemaAtualizarStatusDados dados(StatusProblema status, String motivo) {
        return new ProblemaAtualizarStatusDados(1L, status, motivo);
    }

    private Problema problemaPendente(Long unidadeId) {
        Unidade unidade = new Unidade(); unidade.setId(unidadeId);
        LocalUnidade local = new LocalUnidade(); local.setUnidade(unidade);
        Problema problema = new Problema(); problema.setId(1L); problema.setLocalUnidade(local);
        problema.setStatus(StatusProblema.PENDENTE);
        return problema;
    }

    private Usuario usuario(Long unidadeId) {
        Unidade unidade = new Unidade(); unidade.setId(unidadeId);
        Usuario usuario = new Usuario(); usuario.setId(2L); usuario.setUnidade(unidade);
        return usuario;
    }
}
