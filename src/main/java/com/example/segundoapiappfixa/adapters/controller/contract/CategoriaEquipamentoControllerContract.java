package com.example.segundoapiappfixa.adapters.controller.contract;

import com.example.segundoapiappfixa.adapters.dto.input.CategoriaEquipamento.CategoriaEquipamentoAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.input.CategoriaEquipamento.CategoriaEquipamentoCadastrarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.CategoriaEquipamento.CategoriaEquipamentoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosCategoriaEquipamentoQueryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CategoriaEquipamentoControllerContract {
    ResponseEntity<List<CategoriaEquipamentoOutputDTO>> listar(FiltrosCategoriaEquipamentoQueryParam filtros, String campos, Authentication authentication);

    ResponseEntity<CategoriaEquipamentoOutputDTO> listarDetalhes(Long categoriaEquipamentoId, String campos, Authentication authentication);

    ResponseEntity<CategoriaEquipamentoOutputDTO> cadastrar(CategoriaEquipamentoCadastrarInputDTO dto, Authentication authentication);

    ResponseEntity<CategoriaEquipamentoOutputDTO> atualizar(CategoriaEquipamentoAtualizarInputDTO dto, Authentication authentication);

    ResponseEntity<CategoriaEquipamentoOutputDTO> deletar(Long categoriaId, Authentication authentication);
}
