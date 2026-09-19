package com.example.segundoapiappfixa.infrastructure.config;

import com.example.segundoapiappfixa.infrastructure.exception.CustomAcessDeniedHandler;
import com.example.segundoapiappfixa.infrastructure.security.JwtTokenFilter;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig{

    private final CustomAcessDeniedHandler customAcessDeniedHandler;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws  Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

        return http
               .csrf(AbstractHttpConfigurer::disable)
               .formLogin(AbstractHttpConfigurer::disable)
               .sessionManagement(
                       session -> session.sessionCreationPolicy(
                               SessionCreationPolicy.STATELESS
                       ))
               .addFilterBefore(
                       jwtTokenFilter,
                       UsernamePasswordAuthenticationFilter.class
               )
                .authorizeHttpRequests(auth -> auth

                        // Permissão de Acesso para CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/error").permitAll()

                        // Endpoints de Autenticação
                        .requestMatchers(
                                "/api/v1/auth/login",
                                "/api/v1/auth/refresh"
                        ).permitAll()
                        .requestMatchers("/api/v1/auth/logout").authenticated()

                        // GET
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/solicitacoes/minhas",
                                "/api/v1/solicitacoes/selecionar/**",
                                "/api/v1/eventos/**"
                        ).hasAnyRole("GESTOR", "TECNICO", "SOLICITANTE")
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/os/minhas",
                                "/api/v1/tarefas/**",
                                "/api/v1/equipamentos/**"
                        ).hasAnyRole("GESTOR", "TECNICO")

                        // POST
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/solicitacoes"
                        ).hasAnyRole("GESTOR", "TECNICO", "SOLICITANTE")

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/ocorrencias/**"
                        ).hasAnyRole("GESTOR", "TECNICO")

                        // PATCH
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/v1/ocorrencias/**"
                        ).hasAnyRole("GESTOR", "TECNICO")

                        // DELETE
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/ocorrencias/**"
                        ).hasAnyRole("GESTOR", "TECNICO")

                        .requestMatchers(
                                "/api/v1/**"
                        ).hasRole("GESTOR")

                        .anyRequest().authenticated()

                )
               .exceptionHandling(
                       exception -> exception.accessDeniedHandler(customAcessDeniedHandler))
               .build();
    }
}
