package com.example.segundoapiappfixa.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI fixaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Fixa API")
                        .description("API do aplicativo de gestão de manutenção Fixa.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipe Fixa")
                                .email("fixa.inter2026@gmail.com")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")
                        )
                )

                .tags(List.of(
                        new Tag()
                                .name("Autenticação").description("Login, renovação e encerramento de sessões."),

                        new Tag()
                                .name("Perfil")
                                .description("Consulta dos dados do usuário autenticado."),

                        new Tag()
                                .name("Categorias de equipamento")
                                .description("Cadastro e consulta das categorias utilizadas para classificar equipamentos."),

                        new Tag()
                                .name("Marcas de equipamento")
                                .description("Cadastro e consulta de marcas de equipamentos."),

                        new Tag()
                                .name("Modelos de equipamento")
                                .description("Cadastro e consulta dos modelos vinculados a marcas e categorias."),

                        new Tag()
                                .name("Equipamentos")
                                .description("Cadastro, consulta e manutenção dos equipamentos da instituição."),

                        new Tag()
                                .name("Eventos")
                                .description("Registro e consulta de eventos associados aos locais e usuários."),

                        new Tag()
                                .name("Ocorrências")
                                .description("Registro e acompanhamento de ocorrências operacionais."),

                        new Tag()
                                .name("Solicitações")
                                .description("Abertura, consulta e atualização de solicitações de atendimento."),

                        new Tag()
                                .name("Ordens de serviço")
                                .description("Gerenciamento das ordens de serviço originadas de solicitações."),

                        new Tag()
                                .name("Tarefas")
                                .description("Gerenciamento das tarefas vinculadas às ordens de serviço."),

                        new Tag()
                                .name("Técnicos")
                                .description("Consulta de técnicos, competências e ordens de serviço atribuídas."),

                        new Tag()
                                .name("Erros")
                                .description("Respostas padronizadas para validação, autenticação e falhas da API.")
                ))

                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
