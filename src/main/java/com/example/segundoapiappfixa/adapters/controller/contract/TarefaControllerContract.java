package com.example.segundoapiappfixa.adapters.controller.contract;

import com.example.segundoapiappfixa.adapters.dto.input.Tarefa.*;
import com.example.segundoapiappfixa.adapters.dto.output.Tarefa.TarefaOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosTarefaQueryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TarefaControllerContract {
    ResponseEntity<List<TarefaOutputDTO>> listar(Long ordemServicoId, String campos, FiltrosTarefaQueryParam filtros, Authentication authentication);

    ResponseEntity<TarefaOutputDTO> listarDetalhes(Long tarefaId, String campos, Authentication authentication);

    ResponseEntity<List<TarefaOutputDTO>> cadastrar(List<TarefaCriarInputDTO> tarefaCriarInputDTOS, Authentication authentication);

    ResponseEntity<TarefaOutputDTO> atualizar(TarefaAtualizarInputDTO atualizarInputDTO, Authentication authentication);

    ResponseEntity<TarefaOutputDTO> deletar(Long tarefaId, Authentication authentication);
}
