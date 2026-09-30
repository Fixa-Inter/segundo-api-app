package com.example.segundoapiappfixa.auth.dto;

import jakarta.validation.constraints.NotNull;

public record RedefinirSenhaRequestDTO (
        String email,
        Integer codigo,
        String novaSenha
){

}
