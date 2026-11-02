package com.example.segundoapiappfixa.domain.service.auth;

import java.util.Date;

public interface EmissorAccessToken {
    String generateAccessToken(Long id, String email, String role, Date issuedAt);
}
