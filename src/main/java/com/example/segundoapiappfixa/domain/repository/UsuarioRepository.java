package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosUsuarioQueryParam;
import java.util.Map;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    Map<String, String> camposNormalizados = Map.of("nome_completo", "nomeCompleto", "email", "email", "cargo", "cargo", "cnpj_endereco", "endereco.cnpj", "esta_ativo", "estaAtivo");

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findById(Long id);

    List<Usuario> findByEnderecoIdAndTipoAcesso(Long enderecoId, TipoAcesso tipoAcesso, FiltrosUsuarioQueryParam filtros);

    Usuario save(Usuario usuario);

}
