package com.example.segundoapiappfixa.auth.usecase;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.application.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.application.exception.RegraProblemaException;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.model.Unidade;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.model.auth.CadastroDados;
import com.example.segundoapiappfixa.domain.model.auth.LoginResultado;
import com.example.segundoapiappfixa.domain.repository.UnidadeRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.domain.service.auth.CodificadorSenha;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@UseCase
@RequiredArgsConstructor
public class Cadastrar {
    private final UsuarioRepository usuarioRepository;
    private final UnidadeRepository unidadeRepository;
    private final CodificadorSenha codificadorSenha;
    private final GerenciarSessao sessao;

    public LoginResultado cadastrar(CadastroDados dados) {
        String email = dados.email().trim().toLowerCase(Locale.ROOT);
        if (usuarioRepository.findByEmail(email).isPresent())
            throw new RegraProblemaException("exception.usuario.emailAlreadyExists");
        if (dados.dataNascimento().isAfter(LocalDate.now().minusYears(12)))
            throw new RegraProblemaException("validation.usuario.dataNascimento.minimumAge");
        Unidade unidade = unidadeRepository.findById(dados.unidadeId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.unidade.notFound"));
        String dominio = unidade.getInstituicao().getDominioEmail().toLowerCase(Locale.ROOT);
        if (!dominio.equals(email.substring(email.indexOf('@') + 1)))
            throw new RegraProblemaException("validation.usuario.emailDomain.invalid");
        Usuario usuario = new Usuario(null, unidade, dados.nomeCompleto().trim(), email,
                TipoAcesso.SOLICITANTE, codificadorSenha.codificar(dados.senha()), dados.cargo().trim(),
                dados.dataNascimento(), LocalDateTime.now(), true, false, List.of(), null);
        Usuario salvo = usuarioRepository.save(usuario);
        return new LoginResultado(salvo.getEmail(), sessao.criarSessao(salvo));
    }
}
