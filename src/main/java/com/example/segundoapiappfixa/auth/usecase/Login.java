package com.example.segundoapiappfixa.auth.usecase;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.application.exception.RegraProblemaException;
import com.example.segundoapiappfixa.application.exception.UsuarioInativoException;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.model.auth.IdentidadeGoogle;
import com.example.segundoapiappfixa.domain.model.auth.LoginGoogleResultado;
import com.example.segundoapiappfixa.domain.model.auth.LoginResultado;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.domain.service.auth.AutenticadorCredenciais;
import com.example.segundoapiappfixa.domain.service.auth.VerificadorIdentidadeGoogle;
import lombok.RequiredArgsConstructor;
import java.util.Locale;

@UseCase
@RequiredArgsConstructor
public class Login {
    private final AutenticadorCredenciais autenticador;
    private final VerificadorIdentidadeGoogle google;
    private final UsuarioRepository usuarios;
    private final GerenciarSessao sessao;

    public LoginResultado login(String email, String senha) {
        String nomeAutenticado = autenticador.autenticar(email, senha);
        Usuario usuario = usuarios.findByEmail(email).orElse(null);
        if (usuario == null || !Boolean.TRUE.equals(usuario.getEstaAtivo())) throw new UsuarioInativoException();
        return new LoginResultado(nomeAutenticado, sessao.criarSessao(usuario));
    }

    public LoginGoogleResultado loginGoogle(String idToken) {
        IdentidadeGoogle identidade = google.verificar(idToken);
        if (!identidade.emailVerificado()) throw new RegraProblemaException("validation.auth.google.emailNotVerified");
        String email = identidade.email().trim().toLowerCase(Locale.ROOT);
        Usuario usuario = usuarios.findByEmail(email).orElse(null);
        if (usuario == null) return new LoginGoogleResultado("REGISTRATION_REQUIRED", email, identidade.nome(), null);
        if (!Boolean.TRUE.equals(usuario.getEstaAtivo())) throw new UsuarioInativoException();
        return new LoginGoogleResultado("AUTHENTICATED", usuario.getEmail(), usuario.getNomeCompleto(), sessao.criarSessao(usuario));
    }
}
