package com.example.segundoapiappfixa.auth.usecase;

import com.example.segundoapiappfixa.application.exception.RegraProblemaException;
import com.example.segundoapiappfixa.domain.model.Instituicao;
import com.example.segundoapiappfixa.domain.model.Unidade;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.model.auth.CadastroDados;
import com.example.segundoapiappfixa.domain.model.auth.TokensAutenticacao;
import com.example.segundoapiappfixa.domain.repository.UnidadeRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.domain.service.auth.CodificadorSenha;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CadastrarTest {
    @Test
    void cadastraComSenhaCodificadaEIniciaSessao() {
        var usuarios = mock(UsuarioRepository.class);
        var unidades = mock(UnidadeRepository.class);
        var codificador = mock(CodificadorSenha.class);
        var sessao = mock(GerenciarSessao.class);
        var instituicao = new Instituicao(); instituicao.setDominioEmail("escola.com");
        var unidade = new Unidade(); unidade.setInstituicao(instituicao);
        when(unidades.findById(1L)).thenReturn(Optional.of(unidade));
        when(codificador.codificar("senha")).thenReturn("hash");
        when(usuarios.save(any())).thenAnswer(call -> call.getArgument(0));
        when(sessao.criarSessao(any())).thenReturn(new TokensAutenticacao("access", "refresh"));
        var dados = new CadastroDados(" Ana ", " ANA@ESCOLA.COM ", "senha", 1L, "Cargo", LocalDate.of(2000, 1, 1));

        var resultado = new Cadastrar(usuarios, unidades, codificador, sessao).cadastrar(dados);

        assertEquals("ana@escola.com", resultado.email());
        assertEquals("refresh", resultado.tokens().refreshToken());
        verify(usuarios).save(argThat(usuario -> "hash".equals(usuario.getSenhaHash()) && "Ana".equals(usuario.getNomeCompleto())));
    }

    @Test
    void bloqueiaEmailJaExistente() {
        var usuarios = mock(UsuarioRepository.class);
        var sessao = mock(GerenciarSessao.class);
        when(usuarios.findByEmail("ana@escola.com")).thenReturn(Optional.of(new Usuario()));
        var cadastrar = new Cadastrar(usuarios, mock(UnidadeRepository.class), mock(CodificadorSenha.class), sessao);
        var dados = new CadastroDados("Ana", "ana@escola.com", "senha", 1L, "Cargo", LocalDate.of(2000, 1, 1));
        assertThrows(RegraProblemaException.class, () -> cadastrar.cadastrar(dados));
        verify(usuarios, never()).save(any());
        verifyNoInteractions(sessao);
    }
}
