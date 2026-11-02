package com.example.segundoapiappfixa.application.security;

import com.example.segundoapiappfixa.application.exception.RegraProblemaException;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;

/** Classificação das permissões usada pelas regras dos casos de uso. */
public final class Permissoes {
    private Permissoes() { }

    public static void validar(TipoAcesso permissao) {
        if (permissao == null) throw new RegraProblemaException("exception.access.denied");
    }

    public static boolean gestor(TipoAcesso permissao) {
        return permissao == TipoAcesso.GESTOR || permissao == TipoAcesso.ADMINISTRADOR;
    }

    public static boolean administrador(TipoAcesso permissao) {

        return permissao == TipoAcesso.ADMINISTRADOR;
    }

    public static void validarOperacional(TipoAcesso permissao) {
        if (!gestor(permissao) && permissao != TipoAcesso.TECNICO) {
            throw new RegraProblemaException("exception.access.denied");
        }
    }

    public static void validarGestao(TipoAcesso permissao) {
        if (!gestor(permissao)) throw new RegraProblemaException("exception.access.denied");
    }
}
