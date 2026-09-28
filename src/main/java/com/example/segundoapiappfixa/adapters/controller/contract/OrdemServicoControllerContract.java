package com.example.segundoapiappfixa.adapters.controller.contract;

import com.example.segundoapiappfixa.adapters.dto.input.OrdemServico.*;
import com.example.segundoapiappfixa.adapters.dto.output.OrdemServico.OrdemServicoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosOrdemServicoQueryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface OrdemServicoControllerContract {
    ResponseEntity<List<OrdemServicoOutputDTO>> listar(String campos, FiltrosOrdemServicoQueryParam filtros, Authentication authentication);

    ResponseEntity<List<OrdemServicoOutputDTO>> listarMinhas(String campos, FiltrosOrdemServicoQueryParam filtros, Authentication authentication);

    ResponseEntity<OrdemServicoOutputDTO> listarDetalhes(Long ordemServicoId, String campos, Authentication authentication);

    ResponseEntity<OrdemServicoOutputDTO> cadastrar(OrdemServicoCadastrarInputDTO input, Authentication authentication);

    ResponseEntity<OrdemServicoOutputDTO> atualizar(OrdemServicoAtualizarInputDTO input, Authentication authentication);

    ResponseEntity<OrdemServicoOutputDTO> deletarOrdemServico(Long ordemServicoId, Authentication authentication);
}
