package com.example.segundoapiappfixa.auth.controller;

import com.example.segundoapiappfixa.auth.controller.contract.AuthControllerContract;
import com.example.segundoapiappfixa.auth.dto.LoginRequestDTO;
import com.example.segundoapiappfixa.auth.dto.LoginResponseDTO;
import com.example.segundoapiappfixa.auth.dto.RedefinirSenhaOutputDTO;
import com.example.segundoapiappfixa.auth.dto.RedefinirSenhaRequestDTO;
import com.example.segundoapiappfixa.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerContract {

    private final AuthService service;

    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(service.login(requestDTO));
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
}
