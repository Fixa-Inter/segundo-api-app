package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosUsuarioQueryParam;

import java.util.Map;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    Map<String, String> camposNormalizados = Map.of(
            "nome_completo", "nomeCompleto",
            "email", "email",
            "cargo", "cargo",
            "cnpj_unidade", "unidade.cnpj",
            "esta_ativo", "estaAtivo"
    );

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findById(Long id);

    List<Usuario> findByUnidadeIdAndTipoAcesso(
            Long unidadeId,
            TipoAcesso tipoAcesso,
            FiltrosUsuarioQueryParam filtros
    );

    List<Usuario> findGestoresAtivosByUnidadeId(Long unidadeId);

    List<Usuario> findTecnicosRecomendados(
            Long unidadeId,
            CategoriaProblema categoriaProblema
    );

    Usuario save(Usuario usuario);

}
