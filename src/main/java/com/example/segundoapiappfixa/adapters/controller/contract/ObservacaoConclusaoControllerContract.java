package com.example.segundoapiappfixa.adapters.controller.contract;

import com.example.segundoapiappfixa.adapters.dto.input.ObservacaoConclusao.*;
import com.example.segundoapiappfixa.adapters.dto.output.ObservacaoConclusao.ObservacaoConclusaoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosObservacaoConclusaoQueryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ObservacaoConclusaoControllerContract {
    ResponseEntity<List<ObservacaoConclusaoOutputDTO>> listar(
            String campos,
            FiltrosObservacaoConclusaoQueryParam filtros,
            Authentication authentication
    );

    ResponseEntity<List<ObservacaoConclusaoOutputDTO>> listarMinhas(
            String campos,
            FiltrosObservacaoConclusaoQueryParam filtros,
            Authentication authentication
    );

    ResponseEntity<ObservacaoConclusaoOutputDTO> listarDetalhes(
            Long id,
            String campos,
            Authentication authentication
    );

    ResponseEntity<ObservacaoConclusaoOutputDTO> criar(
            ObservacaoConclusaoCriarInputDTO input,
            Authentication authentication
    );

    ResponseEntity<ObservacaoConclusaoOutputDTO> atualizar(
            ObservacaoConclusaoAtualizarInputDTO input,
            Authentication authentication
    );

    ResponseEntity<ObservacaoConclusaoOutputDTO> deletar(
            Long id,
            Authentication authentication
    );
}
