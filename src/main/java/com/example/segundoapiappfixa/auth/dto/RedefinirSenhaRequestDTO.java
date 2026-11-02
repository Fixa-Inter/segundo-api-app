package com.example.segundoapiappfixa.auth.dto;

public record RedefinirSenhaRequestDTO (
        String email,
        Integer codigo,
        String novaSenha
){

}
