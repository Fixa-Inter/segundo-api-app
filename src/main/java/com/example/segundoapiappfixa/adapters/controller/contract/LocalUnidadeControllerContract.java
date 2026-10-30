package com.example.segundoapiappfixa.adapters.controller.contract;

import com.example.segundoapiappfixa.adapters.dto.output.LocalUnidade.LocalUnidadeOutputDTO;
import com.example.segundoapiappfixa.adapters.utils.ControllerUtils;
import com.example.segundoapiappfixa.application.usecase.Problema.ListarLocalUnidade;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface LocalUnidadeControllerContract {

    public ResponseEntity<List<LocalUnidadeOutputDTO>> listarLocalUnidade(
            Authentication authentication
    );

}
