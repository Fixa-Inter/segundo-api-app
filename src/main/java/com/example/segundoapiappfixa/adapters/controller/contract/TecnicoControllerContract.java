package com.example.segundoapiappfixa.adapters.controller.contract;

import com.example.segundoapiappfixa.adapters.dto.input.Aptidao.AptidaoAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Aptidao.AptidaoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.OrdemServico.OrdemServicoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Usuario.UsuarioOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosUsuarioQueryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TecnicoControllerContract {

    ResponseEntity<List<UsuarioOutputDTO>> listar(String campos, FiltrosUsuarioQueryParam filtros, Authentication authentication);

    ResponseEntity<List<AptidaoOutputDTO>> listarCompetencias(Long usuarioId, String campos, Authentication authentication);

    ResponseEntity<List<OrdemServicoOutputDTO>> listarOrdensServico(Long usuarioId, String campos, Authentication authentication);

    ResponseEntity<AptidaoOutputDTO> atualizarAptidao(AptidaoAtualizarInputDTO dto, Authentication authentication);
}
