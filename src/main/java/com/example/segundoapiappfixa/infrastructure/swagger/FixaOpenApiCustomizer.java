package com.example.segundoapiappfixa.infrastructure.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FixaOpenApiCustomizer implements OpenApiCustomizer {

    @Override
    public void customise(OpenAPI openAPI) {
        if (openAPI.getPaths() == null) return;

        aplicar(openAPI, CategoriaEquipamentoSwagger.endpoints());
        aplicar(openAPI, AuthSwagger.endpoints());
        aplicar(openAPI, EquipamentoSwagger.endpoints());
        aplicar(openAPI, EventoSwagger.endpoints());
        aplicar(openAPI, MarcaEquipamentoSwagger.endpoints());
        aplicar(openAPI, ModeloEquipamentoSwagger.endpoints());
        aplicar(openAPI, OcorrenciaSwagger.endpoints());
        aplicar(openAPI, OrdemServicoSwagger.endpoints());
        aplicar(openAPI, ProblemaSwagger.endpoints());
        aplicar(openAPI, TarefaSwagger.endpoints());
        aplicar(openAPI, TecnicoSwagger.endpoints());
        aplicar(openAPI, PerfilSwagger.endpoints());
    }

    private void aplicar(OpenAPI openAPI, List<SwaggerEndpoint> endpoints) {
        endpoints.forEach(endpoint -> {
            PathItem pathItem = openAPI.getPaths().get(endpoint.path());
            if (pathItem == null) return;

            Operation operation = obterOperacao(pathItem, endpoint.method());
            if (operation == null) return;

            operation.setTags(List.of(endpoint.tag()));
            operation.setSummary(endpoint.summary());
            operation.setDescription(endpoint.description());

            if (!"Autenticação".equals(endpoint.tag())) {
                operation.setSecurity(List.of(
                        new SecurityRequirement().addList("bearerAuth")
                ));
            }
        });
    }

    private Operation obterOperacao(PathItem pathItem, String method) {
        return switch (method) {
            case "GET" -> pathItem.getGet();
            case "POST" -> pathItem.getPost();
            case "PUT" -> pathItem.getPut();
            case "PATCH" -> pathItem.getPatch();
            case "DELETE" -> pathItem.getDelete();
            default -> null;
        };
    }
}
