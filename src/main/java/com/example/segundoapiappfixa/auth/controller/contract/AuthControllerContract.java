package com.example.segundoapiappfixa.auth.controller.contract;

import com.example.segundoapiappfixa.auth.dto.LoginRequestDTO;
import com.example.segundoapiappfixa.auth.dto.LoginResponseDTO;
import org.springframework.http.ResponseEntity;

public interface AuthControllerContract {

    ResponseEntity<LoginResponseDTO> login(LoginRequestDTO requestDTO);
}
