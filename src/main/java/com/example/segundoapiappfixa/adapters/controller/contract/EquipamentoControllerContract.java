package com.example.segundoapiappfixa.adapters.controller.contract;

import com.example.segundoapiappfixa.adapters.dto.input.Equipamento.EquipamentoAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.input.Equipamento.EquipamentoCadastrarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Equipamento.EquipamentoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosEquipamentoQueryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface EquipamentoControllerContract {
    ResponseEntity<List<EquipamentoOutputDTO>> listar(Long modeloEquipamentoId, FiltrosEquipamentoQueryParam filtros, String campos, Authentication authentication);

    ResponseEntity<EquipamentoOutputDTO> listarDetalhes(Long equipamentoId, String campos, Authentication authentication);

    ResponseEntity<EquipamentoOutputDTO> cadastrar(EquipamentoCadastrarInputDTO dto, Authentication authentication);

    ResponseEntity<EquipamentoOutputDTO> atualizar(EquipamentoAtualizarInputDTO dto, Authentication authentication);

    ResponseEntity<EquipamentoOutputDTO> deletar(Long equipamentoId, Authentication authentication);
}
