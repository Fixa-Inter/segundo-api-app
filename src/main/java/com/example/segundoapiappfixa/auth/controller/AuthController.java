package com.example.segundoapiappfixa.auth.controller;

import com.example.segundoapiappfixa.auth.controller.contract.AuthControllerContract;
import com.example.segundoapiappfixa.auth.dto.*;
import com.example.segundoapiappfixa.auth.service.AuthService;
import com.example.segundoapiappfixa.auth.service.ListarOpcoesCadastro;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerContract {

    private final AuthService service;
    private final ListarOpcoesCadastro listarOpcoesCadastro;

    @Override
    @PostMapping("/cadastro")
    public ResponseEntity<LoginResponseDTO> cadastrar(
            @Valid @RequestBody CadastroUsuarioRequestDTO requestDTO
    ) {
        return ResponseEntity.status(201).body(service.cadastrar(requestDTO));
    }

    @GetMapping("/instituicoes")
    public ResponseEntity<List<CadastroNomeOutputDTO>> listarInstituicoes() {
        return ResponseEntity.ok(listarOpcoesCadastro.listarInstituicoes());
    }

    @GetMapping("/instituicoes/{instituicaoId}/unidades")
    public ResponseEntity<List<CadastroNomeOutputDTO>> listarUnidades(
            @PathVariable Long instituicaoId
    ) {
        return ResponseEntity.ok(listarOpcoesCadastro.listarUnidades(instituicaoId));
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(service.login(requestDTO));
    }

    @PostMapping("/login/google")
    public ResponseEntity<GoogleLoginResponseDTO> loginGoogle(
            @Valid @RequestBody GoogleLoginRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(service.loginWithGoogle(requestDTO));
    }

    @PostMapping("/esqueceu-senha/{email}")
    public ResponseEntity<RedefinirSenhaOutputDTO> esqueceuSenha(
            @PathVariable String email
    ) {
        return ResponseEntity.ok(service.esqueceuSenha(email));
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(
            @RequestBody RedefinirSenhaRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(service.redefinirSenha(requestDTO));
    }

    @Override
    @PostMapping("/validar-codigo")
    public ResponseEntity<Boolean> validarCodigo(
            @Valid
            @RequestBody
            ValidarCodigoRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(service.validarCodigo(requestDTO.email(), requestDTO.codigo()));
    }
}
