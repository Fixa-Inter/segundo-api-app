package com.example.segundoapiappfixa.application.security;

import com.example.segundoapiappfixa.application.exception.RegraProblemaException;
import com.example.segundoapiappfixa.domain.model.CategoriaEquipamento;
import com.example.segundoapiappfixa.domain.model.LocalUnidade;
import com.example.segundoapiappfixa.domain.model.ModeloEquipamento;
import com.example.segundoapiappfixa.domain.model.Unidade;
import com.example.segundoapiappfixa.domain.model.Usuario;

/** Valida referências usando a unidade atual do usuário autenticado. */
public final class UnidadeAcesso {
    private UnidadeAcesso() { }

    public static void validarLocal(Usuario usuario, LocalUnidade local) {
        validar(usuario, local == null ? null : local.getUnidade());
    }

    public static void validarCategoria(Usuario usuario, CategoriaEquipamento categoria) {
        Usuario criador = categoria == null ? null : categoria.getUsuario();
        validar(usuario, criador == null ? null : criador.getUnidade());
    }

    public static void validarModelo(Usuario usuario, ModeloEquipamento modelo) {
        validarCategoria(usuario, modelo == null ? null : modelo.getCategoriaEquipamento());
    }

    public static void validarUsuario(Usuario usuario, Usuario referencia) {
        validar(usuario, referencia == null ? null : referencia.getUnidade());
    }

    private static void validar(Usuario usuario, Unidade unidadeReferencia) {
        Unidade unidadeUsuario = usuario == null ? null : usuario.getUnidade();

        if (unidadeUsuario == null || unidadeUsuario.getId() == null || unidadeReferencia == null
                || unidadeReferencia.getId() == null || !unidadeUsuario.getId().equals(unidadeReferencia.getId())) {
            throw new RegraProblemaException("exception.access.denied");
        }
    }
}
