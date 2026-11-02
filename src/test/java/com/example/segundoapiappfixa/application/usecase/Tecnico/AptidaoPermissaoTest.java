package com.example.segundoapiappfixa.application.usecase.Tecnico;

import com.example.segundoapiappfixa.application.exception.RegraProblemaException;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.model.Aptidao;
import com.example.segundoapiappfixa.domain.model.Unidade;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.model.input.Aptidao.AptidaoAtualizarDados;
import com.example.segundoapiappfixa.domain.repository.AptidaoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AptidaoPermissaoTest {
    @Test
    void perfilRecebidoControlaEdicaoDeGestor() {
        AptidaoRepository aptidoes = mock(AptidaoRepository.class);
        UsuarioRepository usuarios = mock(UsuarioRepository.class);
        Unidade unidade = new Unidade(); unidade.setId(1L);
        Usuario autor = new Usuario(); autor.setId(10L); autor.setUnidade(unidade);
        autor.setTipoAcesso(TipoAcesso.GESTOR);
        Usuario alvo = new Usuario(); alvo.setId(20L); alvo.setUnidade(unidade); alvo.setTipoAcesso(TipoAcesso.GESTOR);
        Aptidao aptidao = new Aptidao(); aptidao.setId(30L); aptidao.setUsuario(alvo);
        when(aptidoes.findById(30L)).thenReturn(Optional.of(aptidao));
        when(usuarios.findById(10L)).thenReturn(Optional.of(autor));
        AtualizarAptidao atualizar = new AtualizarAptidao(aptidoes, usuarios);
        AptidaoAtualizarDados dados = new AptidaoAtualizarDados(30L, BigDecimal.TEN, null);

        assertThrows(RegraProblemaException.class, () -> atualizar.atualizar(dados, 10L, TipoAcesso.GESTOR));
        verify(aptidoes, never()).save(any());
        when(aptidoes.save(any())).thenAnswer(call -> call.getArgument(0));
        assertEquals(BigDecimal.TEN, atualizar.atualizar(dados, 10L, TipoAcesso.ADMINISTRADOR).getNota());
    }

    @Test
    void permissaoAusenteOuTecnicoNaoPodeEditarCompetencias() {
        AptidaoRepository aptidoes = mock(AptidaoRepository.class);
        AtualizarAptidao atualizar = new AtualizarAptidao(aptidoes, mock(UsuarioRepository.class));
        AptidaoAtualizarDados dados = new AptidaoAtualizarDados(30L, BigDecimal.TEN, null);
        assertThrows(RegraProblemaException.class, () -> atualizar.atualizar(dados, 10L, null));
        assertThrows(RegraProblemaException.class, () -> atualizar.atualizar(dados, 10L, TipoAcesso.TECNICO));
        verifyNoInteractions(aptidoes);
    }

    @Test
    void somenteAdministradorConsultaCompetenciasDeGestorDaUnidade() {
        AptidaoRepository aptidoes = mock(AptidaoRepository.class);
        UsuarioRepository usuarios = mock(UsuarioRepository.class);
        Unidade unidade = new Unidade(); unidade.setId(1L);
        Usuario autor = new Usuario(); autor.setId(10L); autor.setUnidade(unidade);
        Usuario alvo = new Usuario(); alvo.setId(20L); alvo.setUnidade(unidade); alvo.setTipoAcesso(TipoAcesso.GESTOR);
        Aptidao aptidao = new Aptidao(); aptidao.setId(30L); aptidao.setUsuario(alvo);
        when(usuarios.findById(10L)).thenReturn(Optional.of(autor));
        when(usuarios.findById(20L)).thenReturn(Optional.of(alvo));
        ListarAptidoes listar = new ListarAptidoes(aptidoes, usuarios);

        assertThrows(RegraProblemaException.class, () -> listar.listar(20L, 10L, TipoAcesso.GESTOR));
        verify(aptidoes, never()).findByUsuarioId(anyLong());
        when(aptidoes.findByUsuarioId(20L)).thenReturn(List.of(aptidao));
        assertEquals(List.of(aptidao), listar.listar(20L, 10L, TipoAcesso.ADMINISTRADOR));
    }
}
