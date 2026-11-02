package com.example.segundoapiappfixa.infrastructure.swagger;

import com.example.segundoapiappfixa.auth.controller.AuthController;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AuthSwaggerTest {
    @Test
    void documentaTodasAsRotasReaisDeAutenticacao() {
        Map<String, String> rotas = Arrays.stream(AuthController.class.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(GetMapping.class) || method.isAnnotationPresent(PostMapping.class))
                .collect(Collectors.toMap(method -> {
                    GetMapping get = method.getAnnotation(GetMapping.class);
                    return get != null ? "GET /api/v1/auth" + get.value()[0]
                            : "POST /api/v1/auth" + method.getAnnotation(PostMapping.class).value()[0];
                }, method -> method.getName()));

        assertEquals(10, rotas.size());
        assertEquals(rotas.keySet(), AuthSwagger.endpoints().stream()
                .map(endpoint -> endpoint.method() + " " + endpoint.path()).collect(Collectors.toSet()));
    }

    @Test
    void contratosDeSessaoERecuperacaoRefletemOsEndpoints() {
        OpenAPI api = new OpenAPI().paths(new Paths());
        for (SwaggerEndpoint endpoint : AuthSwagger.endpoints()) {
            PathItem item = api.getPaths().get(endpoint.path());
            if (item == null) {
                item = new PathItem();
                api.getPaths().addPathItem(endpoint.path(), item);
            }
            if (endpoint.method().equals("GET")) item.setGet(new Operation());
            else item.setPost(new Operation());
        }

        new FixaOpenApiCustomizer().customise(api);

        Operation cadastro = api.getPaths().get("/api/v1/auth/cadastro").getPost();
        assertEquals("Autenticação", cadastro.getTags().getFirst());
        assertNotNull(cadastro.getResponses().get("201"));
        assertNull(cadastro.getResponses().get("200"));
        assertEquals("#/components/schemas/LoginResponseDTO", cadastro.getResponses().get("201")
                .getContent().get("application/json").getSchema().get$ref());
        Operation refresh = api.getPaths().get("/api/v1/auth/refresh").getPost();
        assertTrue(refresh.getRequestBody().getRequired());
        assertNotNull(refresh.getResponses().get("401"));
        assertNotNull(refresh.getResponses().get("401").getContent().get("application/json").getExamples().get("mensagem"));
        assertEquals("#/components/schemas/RefreshTokenResponseDTO", refresh.getResponses().get("200")
                .getContent().get("application/json").getSchema().get$ref());
        Operation logout = api.getPaths().get("/api/v1/auth/logout").getPost();
        assertFalse(logout.getRequestBody().getRequired());
        assertNull(logout.getResponses().get("204").getContent());
        Operation google = api.getPaths().get("/api/v1/auth/login/google").getPost();
        assertEquals("#/components/schemas/GoogleLoginResponseDTO", google.getResponses().get("200")
                .getContent().get("application/json").getSchema().get$ref());
        assertEquals(2, google.getResponses().get("200").getContent().get("application/json").getExamples().size());
        Operation codigo = api.getPaths().get("/api/v1/auth/validar-codigo").getPost();
        assertEquals("boolean", codigo.getResponses().get("200").getContent().get("application/json").getSchema().getType());
        Operation redefinir = api.getPaths().get("/api/v1/auth/redefinir-senha").getPost();
        assertEquals("string", redefinir.getResponses().get("200").getContent().get("text/plain").getSchema().getType());
        for (SwaggerEndpoint endpoint : AuthSwagger.endpoints()) {
            PathItem item = api.getPaths().get(endpoint.path());
            Operation operacao = endpoint.method().equals("GET") ? item.getGet() : item.getPost();
            assertTrue(operacao.getSecurity().isEmpty(), endpoint.path());
        }
    }
}
