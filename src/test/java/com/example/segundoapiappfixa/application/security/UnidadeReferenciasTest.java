package com.example.segundoapiappfixa.application.security;

import com.example.segundoapiappfixa.application.exception.RegraProblemaException;
import com.example.segundoapiappfixa.application.usecase.Equipamento.AtualizarEquipamento;
import com.example.segundoapiappfixa.application.usecase.Equipamento.CadastrarEquipamento;
import com.example.segundoapiappfixa.application.usecase.Evento.AtualizarEvento;
import com.example.segundoapiappfixa.application.usecase.Evento.CadastrarEvento;
import com.example.segundoapiappfixa.application.usecase.ModeloEquipamento.AtualizarModeloEquipamento;
import com.example.segundoapiappfixa.application.usecase.ModeloEquipamento.CadastrarModeloEquipamento;
import com.example.segundoapiappfixa.application.usecase.Ocorrencia.AtualizarOcorrencia;
import com.example.segundoapiappfixa.application.usecase.Ocorrencia.CriarOcorrencia;
import com.example.segundoapiappfixa.application.usecase.Problema.AtualizarProblema;
import com.example.segundoapiappfixa.application.usecase.Problema.CriarProblema;
import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.model.input.Equipamento.EquipamentoAtualizarDados;
import com.example.segundoapiappfixa.domain.model.input.Equipamento.EquipamentoCadastrarDados;
import com.example.segundoapiappfixa.domain.model.input.Evento.EventoAtualizarDados;
import com.example.segundoapiappfixa.domain.model.input.Evento.EventoCadastrarDados;
import com.example.segundoapiappfixa.domain.model.input.ModeloEquipamento.ModeloEquipamentoAtualizarDados;
import com.example.segundoapiappfixa.domain.model.input.ModeloEquipamento.ModeloEquipamentoCadastrarDados;
import com.example.segundoapiappfixa.domain.model.input.Ocorrencia.OcorrenciaAtualizarDados;
import com.example.segundoapiappfixa.domain.model.input.Problema.ProblemaAtualizarDados;
import com.example.segundoapiappfixa.domain.repository.*;
import com.example.segundoapiappfixa.domain.service.NotificacaoSender;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UnidadeReferenciasTest {
    private static final long USUARIO = 1L, LOCAL = 2L, CATEGORIA = 3L, REGISTRO = 4L, MODELO = 5L;

    @Test
    void solicitacaoCriadaRecusaLocalOuCategoriaDeOutraUnidade() {
        for (boolean localEstrangeiro : new boolean[]{true, false}) {
            var problemas = mock(ProblemaRepository.class);
            var locais = mock(LocalUnidadeRepository.class);
            var categorias = mock(CategoriaEquipamentoRepository.class);
            var usuarios = mock(UsuarioRepository.class);
            var notificacoes = mock(NotificacaoSender.class);
            var usuario = usuario(10L);
            var local = local(localEstrangeiro ? 20L : 10L);
            var categoria = categoria(localEstrangeiro ? 10L : 20L);
            var problema = new Problema(); problema.setLocalUnidade(local); problema.setCategoriaEquipamento(categoria);
            when(locais.findById(LOCAL)).thenReturn(Optional.of(local));
            when(categorias.findById(CATEGORIA)).thenReturn(Optional.of(categoria));
            when(usuarios.findById(USUARIO)).thenReturn(Optional.of(usuario));
            var criar = new CriarProblema(problemas, locais, categorias, usuarios, notificacoes);

            assertThrows(RegraProblemaException.class, () -> criar.cadastrar(problema, List.of(), USUARIO));
            verify(problemas, never()).save(any(), any());
            verifyNoInteractions(notificacoes);
        }
    }

    @Test
    void solicitacaoEditadaValidaLocalECategoria() {
        for (boolean localEstrangeiro : new boolean[]{true, false}) {
            var problemas = mock(ProblemaRepository.class);
            var fotos = mock(FotoRepository.class);
            var locais = mock(LocalUnidadeRepository.class);
            var categorias = mock(CategoriaEquipamentoRepository.class);
            var problema = new Problema(); problema.setUsuario(usuario(10L)); problema.setStatus(StatusProblema.PENDENTE);
            when(problemas.findById(REGISTRO)).thenReturn(Optional.of(problema));
            when(locais.findById(LOCAL)).thenReturn(Optional.of(local(localEstrangeiro ? 20L : 10L)));
            when(categorias.findById(CATEGORIA)).thenReturn(Optional.of(categoria(localEstrangeiro ? 10L : 20L)));
            var atualizar = new AtualizarProblema(problemas, fotos, locais, categorias);
            var dados = new ProblemaAtualizarDados(REGISTRO, null, null, LOCAL, null, CATEGORIA, null, null);

            assertThrows(RegraProblemaException.class, () -> atualizar.atualizar(dados, USUARIO));
            verify(problemas, never()).update(any());
        }
    }

    @Test
    void ocorrenciaCriadaEEditadaValidamLocalECategoria() {
        for (boolean localEstrangeiro : new boolean[]{true, false}) {
            var ocorrencias = mock(OcorrenciaRepository.class);
            var usuarios = mock(UsuarioRepository.class);
            var locais = mock(LocalUnidadeRepository.class);
            var categorias = mock(CategoriaEquipamentoRepository.class);
            var fotos = mock(FotoRepository.class);
            var local = local(localEstrangeiro ? 20L : 10L);
            var categoria = categoria(localEstrangeiro ? 10L : 20L);
            var ocorrencia = new Ocorrencia(); ocorrencia.setUsuario(usuario(10L));
            ocorrencia.setLocalUnidade(local); ocorrencia.setCategoriaEquipamento(categoria);
            when(locais.findById(LOCAL)).thenReturn(Optional.of(local));
            when(categorias.findById(CATEGORIA)).thenReturn(Optional.of(categoria));
            when(usuarios.findById(USUARIO)).thenReturn(Optional.of(usuario(10L)));
            when(ocorrencias.findById(REGISTRO)).thenReturn(Optional.of(ocorrencia));
            var criar = new CriarOcorrencia(ocorrencias, usuarios, locais, categorias);
            var atualizar = new AtualizarOcorrencia(ocorrencias, locais, categorias, fotos);
            var dados = new OcorrenciaAtualizarDados(REGISTRO, LOCAL, CATEGORIA, null, null, null, null, null, null, null);

            assertThrows(RegraProblemaException.class, () -> criar.cadastrar(ocorrencia, List.of(), USUARIO));
            assertThrows(RegraProblemaException.class, () -> atualizar.atualizar(dados, USUARIO));
            verify(ocorrencias, never()).save(any(), any());
            verify(ocorrencias, never()).update(any());
        }
    }

    @Test
    void equipamentoCriadoEEditadoValidamLocalEModelo() {
        for (boolean localEstrangeiro : new boolean[]{true, false}) {
            var equipamentos = mock(EquipamentoRepository.class);
            var usuarios = mock(UsuarioRepository.class);
            var modelos = mock(ModeloEquipamentoRepository.class);
            var locais = mock(LocalUnidadeRepository.class);
            var local = local(localEstrangeiro ? 20L : 10L);
            var modelo = modelo(localEstrangeiro ? 10L : 20L);
            var equipamento = new Equipamento(); equipamento.setUsuario(usuario(10L));
            when(usuarios.findById(USUARIO)).thenReturn(Optional.of(usuario(10L)));
            when(locais.findById(LOCAL)).thenReturn(Optional.of(local));
            when(modelos.findById(MODELO)).thenReturn(Optional.of(modelo));
            when(equipamentos.findById(REGISTRO)).thenReturn(Optional.of(equipamento));
            var criar = new CadastrarEquipamento(equipamentos, usuarios, modelos, locais);
            var atualizar = new AtualizarEquipamento(equipamentos, usuarios, modelos, locais);

            assertThrows(RegraProblemaException.class,
                    () -> criar.cadastrar(new EquipamentoCadastrarDados(USUARIO, MODELO, LOCAL, "E1"), USUARIO));
            assertThrows(RegraProblemaException.class,
                    () -> atualizar.atualizar(new EquipamentoAtualizarDados(REGISTRO, MODELO, LOCAL, null), USUARIO));
            verify(equipamentos, never()).save(any());
        }
    }

    @Test
    void eventoCriadoEEditadoRejeitamLocalDeOutraUnidade() {
        var eventos = mock(EventoRepository.class);
        var usuarios = mock(UsuarioRepository.class);
        var locais = mock(LocalUnidadeRepository.class);
        var notificacoes = mock(NotificacaoSender.class);
        var evento = new Evento(); evento.setUsuario(usuario(10L));
        when(usuarios.findById(USUARIO)).thenReturn(Optional.of(usuario(10L)));
        when(locais.findById(LOCAL)).thenReturn(Optional.of(local(20L)));
        when(eventos.findById(REGISTRO)).thenReturn(Optional.of(evento));
        var criar = new CadastrarEvento(eventos, usuarios, locais, notificacoes);
        var atualizar = new AtualizarEvento(eventos, locais);

        assertThrows(RegraProblemaException.class,
                () -> criar.cadastrar(new EventoCadastrarDados(USUARIO, LOCAL, "Evento", null, null, null, null, null), USUARIO));
        assertThrows(RegraProblemaException.class,
                () -> atualizar.atualizar(new EventoAtualizarDados(REGISTRO, LOCAL, null, null, null, null, null, null), USUARIO));
        verify(eventos, never()).save(any());
        verifyNoInteractions(notificacoes);
    }

    @Test
    void referenciasDaMesmaUnidadeSaoAceitasEMissingFalhaFechado() {
        var usuario = usuario(10L);
        assertDoesNotThrow(() -> UnidadeAcesso.validarLocal(usuario, local(10L)));
        assertDoesNotThrow(() -> UnidadeAcesso.validarCategoria(usuario, categoria(10L)));
        assertDoesNotThrow(() -> UnidadeAcesso.validarModelo(usuario, modelo(10L)));
        assertThrows(RegraProblemaException.class, () -> UnidadeAcesso.validarLocal(usuario, new LocalUnidade()));
        assertThrows(RegraProblemaException.class, () -> UnidadeAcesso.validarCategoria(usuario, new CategoriaEquipamento()));
        assertThrows(RegraProblemaException.class, () -> UnidadeAcesso.validarModelo(usuario, new ModeloEquipamento()));
    }

    @Test
    void edicaoDeSolicitacaoAceitaReferenciasDaUnidade() {
        var problemas = mock(ProblemaRepository.class);
        var locais = mock(LocalUnidadeRepository.class);
        var categorias = mock(CategoriaEquipamentoRepository.class);
        var problema = new Problema(); problema.setUsuario(usuario(10L)); problema.setStatus(StatusProblema.PENDENTE);
        when(problemas.findById(REGISTRO)).thenReturn(Optional.of(problema));
        when(locais.findById(LOCAL)).thenReturn(Optional.of(local(10L)));
        when(categorias.findById(CATEGORIA)).thenReturn(Optional.of(categoria(10L)));
        when(problemas.update(any())).thenAnswer(call -> call.getArgument(0));

        new AtualizarProblema(problemas, mock(FotoRepository.class), locais, categorias).atualizar(
                new ProblemaAtualizarDados(REGISTRO, null, null, LOCAL, null, CATEGORIA, null, null), USUARIO);

        assertEquals(LOCAL, problema.getLocalUnidade().getId());
        assertEquals(CATEGORIA, problema.getCategoriaEquipamento().getId());
        verify(problemas).update(problema);
    }

    @Test
    void criacaoEEdicaoDeOcorrenciaAceitamReferenciasDaUnidade() {
        var ocorrencias = mock(OcorrenciaRepository.class);
        var usuarios = mock(UsuarioRepository.class);
        var locais = mock(LocalUnidadeRepository.class);
        var categorias = mock(CategoriaEquipamentoRepository.class);
        var ocorrencia = new Ocorrencia(); ocorrencia.setId(REGISTRO); ocorrencia.setUsuario(usuario(10L));
        ocorrencia.setLocalUnidade(local(10L)); ocorrencia.setCategoriaEquipamento(categoria(10L));
        when(usuarios.findById(USUARIO)).thenReturn(Optional.of(usuario(10L)));
        when(locais.findById(LOCAL)).thenReturn(Optional.of(local(10L)));
        when(categorias.findById(CATEGORIA)).thenReturn(Optional.of(categoria(10L)));
        when(ocorrencias.save(any(), any())).thenReturn(REGISTRO);
        when(ocorrencias.findById(REGISTRO)).thenReturn(Optional.of(ocorrencia));
        when(ocorrencias.update(any())).thenAnswer(call -> call.getArgument(0));

        new CriarOcorrencia(ocorrencias, usuarios, locais, categorias).cadastrar(ocorrencia, List.of(), USUARIO);
        new AtualizarOcorrencia(ocorrencias, locais, categorias, mock(FotoRepository.class)).atualizar(
                new OcorrenciaAtualizarDados(REGISTRO, LOCAL, CATEGORIA, null, null, null, null, null, null, null), USUARIO);

        verify(ocorrencias).save(ocorrencia, List.of());
        verify(ocorrencias).update(ocorrencia);
    }

    @Test
    void criacaoEEdicaoDeEquipamentoAceitamReferenciasDaUnidade() {
        var equipamentos = mock(EquipamentoRepository.class);
        var usuarios = mock(UsuarioRepository.class);
        var modelos = mock(ModeloEquipamentoRepository.class);
        var locais = mock(LocalUnidadeRepository.class);
        var equipamento = new Equipamento(); equipamento.setUsuario(usuario(10L));
        when(usuarios.findById(USUARIO)).thenReturn(Optional.of(usuario(10L)));
        when(locais.findById(LOCAL)).thenReturn(Optional.of(local(10L)));
        when(modelos.findById(MODELO)).thenReturn(Optional.of(modelo(10L)));
        when(equipamentos.findById(REGISTRO)).thenReturn(Optional.of(equipamento));
        when(equipamentos.save(any())).thenAnswer(call -> call.getArgument(0));

        new CadastrarEquipamento(equipamentos, usuarios, modelos, locais).cadastrar(
                new EquipamentoCadastrarDados(USUARIO, MODELO, LOCAL, "E1"), USUARIO);
        new AtualizarEquipamento(equipamentos, usuarios, modelos, locais).atualizar(
                new EquipamentoAtualizarDados(REGISTRO, MODELO, LOCAL, null), USUARIO);

        verify(equipamentos, times(2)).save(any());
        assertEquals(MODELO, equipamento.getModeloEquipamento().getId());
        assertEquals(LOCAL, equipamento.getLocalUnidade().getId());
    }

    @Test
    void edicaoDeEventoAceitaLocalDaUnidade() {
        var eventos = mock(EventoRepository.class);
        var locais = mock(LocalUnidadeRepository.class);
        var evento = new Evento(); evento.setUsuario(usuario(10L));
        when(eventos.findById(REGISTRO)).thenReturn(Optional.of(evento));
        when(locais.findById(LOCAL)).thenReturn(Optional.of(local(10L)));
        when(eventos.save(any())).thenAnswer(call -> call.getArgument(0));

        new AtualizarEvento(eventos, locais).atualizar(
                new EventoAtualizarDados(REGISTRO, LOCAL, null, null, null, null, null, null), USUARIO);

        verify(eventos).save(evento);
        assertEquals(LOCAL, evento.getLocalUnidade().getId());
    }

    @Test
    void modeloNaoAceitaCategoriaDeOutraUnidade() {
        var modelos = mock(ModeloEquipamentoRepository.class);
        var usuarios = mock(UsuarioRepository.class);
        var marcas = mock(MarcaEquipamentoRepository.class);
        var categorias = mock(CategoriaEquipamentoRepository.class);
        when(usuarios.findById(USUARIO)).thenReturn(Optional.of(usuario(10L)));
        when(marcas.findById(6L)).thenReturn(Optional.of(new MarcaEquipamento()));
        when(categorias.findById(CATEGORIA)).thenReturn(Optional.of(categoria(20L)));
        when(modelos.findById(MODELO)).thenReturn(Optional.of(modelo(10L)));

        assertThrows(RegraProblemaException.class, () -> new CadastrarModeloEquipamento(modelos, usuarios, marcas, categorias)
                .cadastrar(new ModeloEquipamentoCadastrarDados(null, 6L, CATEGORIA, "Modelo", "Descrição"), USUARIO));
        assertThrows(RegraProblemaException.class, () -> new AtualizarModeloEquipamento(modelos, usuarios, marcas, categorias)
                .atualizar(new ModeloEquipamentoAtualizarDados(MODELO, null, CATEGORIA, null, null, null), USUARIO));
        verify(modelos, never()).save(any());
    }

    private static Usuario usuario(long unidadeId) {
        var unidade = new Unidade(); unidade.setId(unidadeId);
        var usuario = new Usuario(); usuario.setId(USUARIO); usuario.setUnidade(unidade); return usuario;
    }

    private static LocalUnidade local(long unidadeId) {
        var unidade = new Unidade(); unidade.setId(unidadeId);
        var local = new LocalUnidade(); local.setId(LOCAL); local.setUnidade(unidade); return local;
    }

    private static CategoriaEquipamento categoria(long unidadeId) {
        var categoria = new CategoriaEquipamento(); categoria.setId(CATEGORIA);
        categoria.setUsuario(usuario(unidadeId)); return categoria;
    }

    private static ModeloEquipamento modelo(long unidadeId) {
        var modelo = new ModeloEquipamento(); modelo.setId(MODELO);
        modelo.setCategoriaEquipamento(categoria(unidadeId)); return modelo;
    }
}
