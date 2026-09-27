package com.example.segundoapiappfixa.auth.controller;

import com.example.segundoapiappfixa.auth.controller.contract.AuthControllerContract;
import com.example.segundoapiappfixa.auth.dto.LoginRequestDTO;
import com.example.segundoapiappfixa.auth.dto.LoginResponseDTO;
import com.example.segundoapiappfixa.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
