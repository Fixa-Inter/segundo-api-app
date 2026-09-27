package com.example.segundoapiappfixa.adapters.controller.contract;

import com.example.segundoapiappfixa.adapters.dto.input.MarcaEquipamento.*;
import com.example.segundoapiappfixa.adapters.dto.output.MarcaEquipamento.MarcaEquipamentoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosMarcaEquipamentoQueryParam;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MarcaEquipamentoControllerContract {
    ResponseEntity<List<MarcaEquipamentoOutputDTO>> listar(String campos, FiltrosMarcaEquipamentoQueryParam filtros);

    ResponseEntity<MarcaEquipamentoOutputDTO> listarDetalhes(Long marcaEquipamentoId, String campos);

    ResponseEntity<MarcaEquipamentoOutputDTO> cadastrar(MarcaEquipamentoCadastrarInputDTO dto);

    ResponseEntity<MarcaEquipamentoOutputDTO> atualizar(MarcaEquipamentoAtualizarInputDTO dto);

    ResponseEntity<MarcaEquipamentoOutputDTO> deletar(Long marcaEquipamentoId);
}
