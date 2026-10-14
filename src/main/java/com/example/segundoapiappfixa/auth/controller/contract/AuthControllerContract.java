package com.example.segundoapiappfixa.auth.controller.contract;

import com.example.segundoapiappfixa.auth.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AuthControllerContract {

    ResponseEntity<LoginResponseDTO> login(LoginRequestDTO requestDTO);

    ResponseEntity<String> redefinirSenha(RedefinirSenhaRequestDTO requestDTO);

    public ResponseEntity<RedefinirSenhaOutputDTO> esqueceuSenha(String email);

    ResponseEntity<List<CadastroNomeOutputDTO>> listarInstituicoes();

    ResponseEntity<List<CadastroNomeOutputDTO>> listarEnderecos(Long instituicaoId);
}
