package com.example.segundoapiappfixa.adapters.controller.contract;

import com.example.segundoapiappfixa.adapters.dto.input.Ocorrencia.*;
import com.example.segundoapiappfixa.adapters.dto.output.Ocorrencia.OcorrenciaOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosOcorrenciaQueryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface OcorrenciaControllerContract {
    ResponseEntity<List<OcorrenciaOutputDTO>> listar(String campos, FiltrosOcorrenciaQueryParam filtros, Authentication authentication);

    ResponseEntity<List<OcorrenciaOutputDTO>> listarMinhas(String campos, FiltrosOcorrenciaQueryParam filtros, Authentication authentication);

    ResponseEntity<OcorrenciaOutputDTO> listarDetalhes(Long ocorrenciaId, String campos, Authentication authentication);

    ResponseEntity<OcorrenciaOutputDTO> cadastrar(OcorrenciaCadastrarInputDTO input, Authentication authentication);

    ResponseEntity<OcorrenciaOutputDTO> atualizar(OcorrenciaAtualizarInputDTO input, Authentication authentication);

    ResponseEntity<OcorrenciaOutputDTO> deletar(Long ocorrenciaId, Authentication authentication);
}
