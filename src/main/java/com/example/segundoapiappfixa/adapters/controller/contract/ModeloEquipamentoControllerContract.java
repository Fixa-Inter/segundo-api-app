package com.example.segundoapiappfixa.adapters.controller.contract;

import com.example.segundoapiappfixa.adapters.dto.input.ModeloEquipamento.*;
import com.example.segundoapiappfixa.adapters.dto.output.ModeloEquipamento.ModeloEquipamentoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosModeloEquipamentoQueryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ModeloEquipamentoControllerContract {
    ResponseEntity<List<ModeloEquipamentoOutputDTO>> listar(String campos, FiltrosModeloEquipamentoQueryParam filtros, Authentication authentication);

    ResponseEntity<ModeloEquipamentoOutputDTO> listarDetalhes(Long modeloEquipamentoId, String campos, Authentication authentication);

    ResponseEntity<ModeloEquipamentoOutputDTO> cadastrar(ModeloEquipamentoCadastrarInputDTO dto, Authentication authentication);

    ResponseEntity<ModeloEquipamentoOutputDTO> atualizar(ModeloEquipamentoAtualizarInputDTO dto, Authentication authentication);

    ResponseEntity<ModeloEquipamentoOutputDTO> deletar(Long modeloEquipamentoId, Authentication authentication);
}
