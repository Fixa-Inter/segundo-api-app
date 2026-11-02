package com.example.segundoapiappfixa.infrastructure.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Method;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class AdapterSwaggerTest {
    @Test
    void todasAsRotasDosControllersRecebemDocumentacao() throws Exception {
        var scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(RestController.class));
        OpenAPI api = new OpenAPI().paths(new Paths());
        Set<String> rotas = new HashSet<>();
        for (var bean : scanner.findCandidateComponents("com.example.segundoapiappfixa.adapters.controller")) {
            Class<?> controller = Class.forName(bean.getBeanClassName());
            RequestMapping base = controller.getAnnotation(RequestMapping.class);
            if (base == null) continue;
            for (Method metodo : controller.getDeclaredMethods()) {
                String http = null; String[] paths = {};
                if (metodo.isAnnotationPresent(GetMapping.class)) { http = "GET"; paths = metodo.getAnnotation(GetMapping.class).value(); }
                else if (metodo.isAnnotationPresent(PostMapping.class)) { http = "POST"; paths = metodo.getAnnotation(PostMapping.class).value(); }
                else if (metodo.isAnnotationPresent(PatchMapping.class)) { http = "PATCH"; paths = metodo.getAnnotation(PatchMapping.class).value(); }
                else if (metodo.isAnnotationPresent(DeleteMapping.class)) { http = "DELETE"; paths = metodo.getAnnotation(DeleteMapping.class).value(); }
                if (http == null) continue;
                if (paths.length == 0) paths = new String[]{""};
                for (String suffix : paths) {
                    String path = base.value()[0] + suffix;
                    assertTrue(rotas.add(http + " " + path));
                    PathItem item = api.getPaths().computeIfAbsent(path, ignored -> new PathItem());
                    Operation operation = new Operation().responses(new ApiResponses().addApiResponse("200", new ApiResponse().description("OK")));
                    switch (http) {
                        case "GET" -> item.setGet(operation);
                        case "POST" -> item.setPost(operation);
                        case "PATCH" -> item.setPatch(operation);
                        case "DELETE" -> item.setDelete(operation);
                    }
                }
            }
        }
        assertFalse(rotas.isEmpty());
        new FixaOpenApiCustomizer().customise(api);
        api.getPaths().forEach((path, item) -> item.readOperationsMap().forEach((method, op) -> {
            assertNotNull(op.getSummary(), method + " " + path);
            assertNotNull(op.getDescription(), method + " " + path);
            assertNotNull(op.getSecurity(), method + " " + path);
        }));
        assertNotNull(api.getPaths().get("/api/v1/notificacao/dispositivo").getPost().getResponses().get("204"));
        assertNotNull(api.getPaths().get("/api/v1/observacoes-conclusao").getPost().getResponses().get("201"));
        var erros = api.getPaths().get("/api/v1/solicitacoes").getGet().getResponses();
        assertNotNull(erros.get("400").getContent().get("application/json").getExamples().get("validacao"));
        assertNotNull(erros.get("403").getContent().get("application/json").getExamples().get("acessoNegado"));
        assertNull(erros.get("401").getContent());
        assertNotNull(erros.get("409").getContent());
    }

    @Test
    void metadadosDeAutenticacaoSaoAplicadosForaDoDto() {
        var senha = new StringSchema();
        var schema = new ObjectSchema().addProperty("senha", senha);
        OpenAPI api = new OpenAPI().components(new Components().addSchemas("LoginRequestDTO", schema));
        AuthSchemaSwagger.complementar(api);
        assertEquals("Senha da conta", senha.getDescription());
        assertTrue(senha.getWriteOnly());
        assertEquals("senha-exemplo", senha.getExample());
    }
}
