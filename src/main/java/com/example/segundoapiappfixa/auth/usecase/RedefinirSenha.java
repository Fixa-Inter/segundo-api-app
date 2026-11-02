package com.example.segundoapiappfixa.auth.usecase;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.application.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.application.exception.RegraProblemaException;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.RecuperarSenhaCodigoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.domain.service.auth.CodificadorSenha;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class RedefinirSenha {
    private final UsuarioRepository usuarios;
    private final RecuperarSenhaCodigoRepository codigos;
    private final CodificadorSenha codificador;
    private final EsqueciSenha recuperacao;

    public String redefinir(String email, Integer codigo, String novaSenha) {
        Usuario usuario = usuarios.findByEmail(email)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("validation.usuario.required"));
        if (!recuperacao.validarCodigo(email, codigo)) throw new RegraProblemaException("validation.codigo.invalido");
        usuario.setSenhaHash(codificador.codificar(novaSenha));
        usuarios.save(usuario);
        codigos.deleteAll(usuario.getId());
        return "Nova senha registrada com sucesso!";
    }
}
