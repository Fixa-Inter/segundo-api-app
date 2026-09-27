package com.example.segundoapiappfixa.adapters.controller.contract;

import com.example.segundoapiappfixa.adapters.dto.input.Problema.*;
import com.example.segundoapiappfixa.adapters.dto.output.Problema.ProblemaOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosProblemaQueryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ProblemaControllerContract {
    ResponseEntity<List<ProblemaOutputDTO>> listar(String campos, FiltrosProblemaQueryParam filtros, Authentication authentication);

    ResponseEntity<List<ProblemaOutputDTO>> listarMinhas(String campos, FiltrosProblemaQueryParam filtros, Authentication authentication);

    ResponseEntity<ProblemaOutputDTO> listarDetalhes(Long problemaId, String campos, Authentication authentication);

    ResponseEntity<ProblemaOutputDTO> cadastrar(ProblemaCriarInputDTO input, Authentication authentication);

    ResponseEntity<ProblemaOutputDTO> atualizaStatus(ProblemaAtualizarStatusDTO input, Authentication authentication);
}
