package com.example.segundoapiappfixa.application.usecase.Tecnico;

import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.model.Unidade;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.AptidaoRepository;
import com.example.segundoapiappfixa.domain.repository.TarefaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ListarTecnicosPermissaoTest {
    @Test
    void administradorIncluiGestoresEGestorListaSomenteTecnicos() {
        UsuarioRepository usuarios = mock(UsuarioRepository.class);
        AptidaoRepository aptidoes = mock(AptidaoRepository.class);
        TarefaRepository tarefas = mock(TarefaRepository.class);
        Unidade unidade = new Unidade(); unidade.setId(10L);
        Usuario usuario = new Usuario(); usuario.setId(1L); usuario.setUnidade(unidade);
        Usuario tecnico = new Usuario(); tecnico.setId(2L);
        Usuario gestor = new Usuario(); gestor.setId(3L);
        when(usuarios.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarios.findByUnidadeIdAndTipoAcesso(10L, TipoAcesso.TECNICO, null)).thenReturn(List.of(tecnico));
        when(usuarios.findByUnidadeIdAndTipoAcesso(10L, TipoAcesso.GESTOR, null)).thenReturn(List.of(gestor));
        when(aptidoes.findCompetenciasVencedorasByUsuarioIds(anyList())).thenReturn(Map.of());
        when(tarefas.countEmAndamentoByTecnicoIds(anyList())).thenReturn(Map.of());
        ListarTecnicos listar = new ListarTecnicos(usuarios, aptidoes, tarefas);

        assertEquals(1, listar.listar(1L, null, TipoAcesso.GESTOR).size());
        verify(usuarios, never()).findByUnidadeIdAndTipoAcesso(10L, TipoAcesso.GESTOR, null);
        assertEquals(2, listar.listar(1L, null, TipoAcesso.ADMINISTRADOR).size());
        verify(usuarios).findByUnidadeIdAndTipoAcesso(10L, TipoAcesso.GESTOR, null);
    }
}
