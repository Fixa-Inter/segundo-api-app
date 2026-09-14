package com.example.segundoapiappfixa.infrastructure.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

@Component
@AllArgsConstructor
@EnableMethodSecurity
public class CustomAcessDeniedHandler implements AccessDeniedHandler {

    // Objeto de conversão, que transforma o MAP no formato de Sring para o JSON
    private final ObjectMapper objectMapper;

   // Manipulador da exceção de acesso negado
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        Map<String, String> errorResponse = Map.of("error", "You have no permission to access this resource");

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));

    }
}
