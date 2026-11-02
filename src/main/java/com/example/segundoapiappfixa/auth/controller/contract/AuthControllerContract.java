package com.example.segundoapiappfixa.auth.controller.contract;

import com.example.segundoapiappfixa.auth.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthControllerContract {

    ResponseEntity<LoginResponseDTO> login(LoginRequestDTO requestDTO);

    ResponseEntity<RefreshTokenResponseDTO> refresh(RefreshTokenRequestDTO requestDTO);

    ResponseEntity<Void> logout(RefreshTokenRequestDTO requestDTO);

    ResponseEntity<GoogleLoginResponseDTO> loginGoogle(GoogleLoginRequestDTO requestDTO);

    ResponseEntity<LoginResponseDTO> cadastrar(CadastroUsuarioRequestDTO requestDTO);

    ResponseEntity<String> redefinirSenha(RedefinirSenhaRequestDTO requestDTO);

    ResponseEntity<Boolean> validarCodigo(ValidarCodigoRequestDTO requestDTO);

    ResponseEntity<RedefinirSenhaOutputDTO> esqueceuSenha(String email);

    ResponseEntity<List<CadastroNomeOutputDTO>> listarInstituicoes();

    ResponseEntity<List<CadastroNomeOutputDTO>> listarUnidades(Long instituicaoId);
}
