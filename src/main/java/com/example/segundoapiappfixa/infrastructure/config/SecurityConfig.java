package com.example.segundoapiappfixa.infrastructure.config;

import com.example.segundoapiappfixa.infrastructure.exception.CustomAcessDeniedHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class SecurityConfig{

    private final CustomAcessDeniedHandler customAcessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

       return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth

                        // Endpoints de Autenticação
                        .requestMatchers("/api/v1/auth/logout").authenticated()
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // GET
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/solicitacoes/usuario/**",
                                "/api/v1/solicitacoes/selecionar/**",
                                "/api/v1/eventos/**"
                        ).hasAnyRole("GESTOR", "TÉCNICO", "SOLICITANTE")
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/tecnicos/selecionar/**",
                                "/api/v1/tecnicos/tarefas/**",
                                "/api/v1/tecnicos/ocorrencias/**",
                                "/api/v1/equipamentos/**"
                        ).hasAnyRole("GESTOR", "TÉCNICO")

                        // POST
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/solicitacoes/**"
                        ).hasAnyRole("GESTOR", "TÉCNICO", "SOLICITANTE")

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/ocorrencias/**"
                        ).hasAnyRole("GESTOR", "TÉCNICO")

                        // PATCH
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/v1/ocorrencias/**"
                        ).hasAnyRole("ADMIN", "TÉCNICO")

                        // DELETE
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/ocorrencias/**"
                        ).hasAnyRole("ADMIN", "TÉCNICO")

                        .requestMatchers(
                                "/api/v1/**"
                        ).hasRole("GESTOR")

                        .anyRequest().authenticated()

                )
               .exceptionHandling(
                       exception -> exception.accessDeniedHandler(customAcessDeniedHandler))
               .formLogin(Customizer.withDefaults())
               .build();
    }
}
