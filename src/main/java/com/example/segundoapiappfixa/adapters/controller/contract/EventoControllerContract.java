package com.example.segundoapiappfixa.adapters.controller.contract;

import com.example.segundoapiappfixa.adapters.dto.input.Evento.*;
import com.example.segundoapiappfixa.adapters.dto.output.Evento.EventoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosEventosQueryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface EventoControllerContract {
    ResponseEntity<List<EventoOutputDTO>> listar(String campos, FiltrosEventosQueryParam filtros, Authentication authentication);

    ResponseEntity<EventoOutputDTO> listarDetalhes(Long eventoId, String campos, Authentication authentication);

    ResponseEntity<EventoOutputDTO> cadastrar(EventoCadastrarInputDTO dto, Authentication authentication);

    ResponseEntity<EventoOutputDTO> atualizar(EventoAtualizarInputDTO dto, Authentication authentication);

    ResponseEntity<EventoOutputDTO> deletar(Long eventoId, Authentication authentication);
}
