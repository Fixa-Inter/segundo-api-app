package com.example.segundoapiappfixa.application.usecase.CategoriaEquipamento;

import com.example.segundoapiappfixa.adapters.dto.input.CategoriaEquipamento.CategoriaEquipamentoCadastrarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.CategoriaEquipamento;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.CategoriaEquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@UseCase
@RequiredArgsConstructor
public class CadastrarCategoriaEquipamento {

    private final CategoriaEquipamentoRepository categoriaEquipamentoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public CategoriaEquipamento cadastrar(
            CategoriaEquipamentoCadastrarInputDTO dto,
            Long usuarioId
    ) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        CategoriaEquipamento categoriaEquipamento = new CategoriaEquipamento(
                null,
                usuario,
                dto.nome(),
                dto.descricao(),
                LocalDateTime.now(),
                true
        );

        return categoriaEquipamentoRepository.save(categoriaEquipamento);
    }
}
