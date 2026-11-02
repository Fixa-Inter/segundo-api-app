package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.filter.FiltrosUsuario;


import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findById(Long id);

    List<Usuario> findByUnidadeIdAndTipoAcesso(
            Long unidadeId,
            TipoAcesso tipoAcesso,
            FiltrosUsuario filtros
    );

    List<Usuario> findGestoresAtivosByUnidadeId(Long unidadeId);

    List<Long> findIdsAtivosByUnidadeId(Long unidadeId);

    List<Usuario> findTecnicosRecomendados(
            Long unidadeId,
            CategoriaProblema categoriaProblema
    );

    Usuario save(Usuario usuario);

}
