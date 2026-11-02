package com.example.segundoapiappfixa.auth.usecase;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.application.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.application.exception.RegraProblemaException;
import com.example.segundoapiappfixa.domain.model.RecuperarSenhaCodigo;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.model.auth.RecuperacaoSenhaResultado;
import com.example.segundoapiappfixa.domain.repository.RecuperarSenhaCodigoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.domain.service.auth.EmailRecuperacaoSender;
import lombok.RequiredArgsConstructor;
import java.util.concurrent.ThreadLocalRandom;

@UseCase
@RequiredArgsConstructor
public class EsqueciSenha {
    private final UsuarioRepository usuarios;
    private final RecuperarSenhaCodigoRepository codigos;
    private final EmailRecuperacaoSender emailSender;

    public RecuperacaoSenhaResultado solicitar(String email) {
        Usuario usuario = usuarios.findByEmail(email)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("validation.usuario.required"));
        if (!Boolean.TRUE.equals(usuario.getEstaAtivo())) throw new RegraProblemaException("validation.usuario.inativo");
        int codigo = ThreadLocalRandom.current().nextInt(100000, 1000000);
        String nome = usuario.getNomeCompleto().split(" ")[0];
        codigos.deleteAll(usuario.getId());
        codigos.save(new RecuperarSenhaCodigo(null, codigo, usuario.getId(), true));
        emailSender.enviarCodigo(usuario.getEmail(), nome, String.valueOf(codigo), "3 minutos");
        return new RecuperacaoSenhaResultado(nome, email, "Código enviado para o e-mail.");
    }

    public boolean validarCodigo(String email, Integer codigo) {
        Usuario usuario = usuarios.findByEmail(email)
                .orElseThrow(() -> new RegraProblemaException("validation.codigo.invalido"));
        RecuperarSenhaCodigo recuperacao = codigos.findByUsuarioIdAndEstaAtivo(usuario.getId());
        return recuperacao != null && codigo != null && codigo.equals(recuperacao.getCodigo());
    }
}
