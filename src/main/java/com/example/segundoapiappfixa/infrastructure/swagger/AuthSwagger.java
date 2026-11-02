package com.example.segundoapiappfixa.infrastructure.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.BooleanSchema;
import io.swagger.v3.oas.models.media.ComposedSchema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Descrição das rotas públicas de autenticação aplicada ao documento OpenAPI gerado.
 */
public final class AuthSwagger {
    private AuthSwagger() {
    }

    public static List<SwaggerEndpoint> endpoints() {
        return List.of(
                endpoint("POST", "/login", "Realizar login", "Autentica por e-mail e senha e retorna access token e refresh token."),
                endpoint("POST", "/login/google", "Entrar com Google", "Valida o ID token Firebase. Retorna AUTHENTICATED com tokens ou REGISTRATION_REQUIRED para uma conta ainda não cadastrada."),
                endpoint("POST", "/cadastro", "Cadastrar usuário", "Cadastra um solicitante e retorna os tokens da nova sessão."),
                endpoint("POST", "/refresh", "Renovar tokens", "Consome o refresh token recebido e retorna um novo par de tokens. O refresh anterior deixa de ser válido."),
                endpoint("POST", "/logout", "Encerrar sessão", "Revoga a família do refresh token informado. Sem corpo ou sem token, encerra com 204 sem revogação."),
                endpoint("GET", "/instituicoes", "Listar instituições", "Retorna as instituições disponíveis para o cadastro."),
                endpoint("GET", "/instituicoes/{instituicaoId}/unidades", "Listar unidades", "Retorna as unidades da instituição selecionada."),
                endpoint("POST", "/esqueceu-senha/{email}", "Solicitar código", "Gera e envia por e-mail um código de recuperação com validade de três minutos."),
                endpoint("POST", "/validar-codigo", "Validar código", "Retorna true para um código válido e false para código inválido ou expirado de usuário existente."),
                endpoint("POST", "/redefinir-senha", "Redefinir senha", "Valida o código, altera a senha e remove o código de recuperação.")
        );
    }

    private static SwaggerEndpoint endpoint(String method, String path, String summary, String description) {
        return new SwaggerEndpoint(method, "/api/v1/auth" + path, "Autenticação", summary, description);
    }

    public static void complementar(OpenAPI api) {
        configurar(api, "/login", PathItem.HttpMethod.POST, "LoginRequestDTO", true, "200", ref("LoginResponseDTO"), "application/json", "Login realizado.", "400", "500");
        configurar(api, "/login/google", PathItem.HttpMethod.POST, "GoogleLoginRequestDTO", true, "200", ref("GoogleLoginResponseDTO"), "application/json", "Conta autenticada ou cadastro necessário.", "400", "403", "500");
        configurar(api, "/cadastro", PathItem.HttpMethod.POST, "CadastroUsuarioRequestDTO", true, "201", ref("LoginResponseDTO"), "application/json", "Usuário cadastrado e sessão criada.", "400", "403", "404", "500");
        configurar(api, "/refresh", PathItem.HttpMethod.POST, "RefreshTokenRequestDTO", true, "200", ref("RefreshTokenResponseDTO"), "application/json", "Novo par de tokens.", "400", "401", "500");
        configurar(api, "/logout", PathItem.HttpMethod.POST, null, false, "204", null, null, "Operação concluída, sem corpo de resposta.", "400", "500");
        configurar(api, "/instituicoes", PathItem.HttpMethod.GET, null, false, "200", new ArraySchema().items(ref("CadastroNomeOutputDTO")), "application/json", "Lista de instituições; pode estar vazia.", "500");
        configurar(api, "/instituicoes/{instituicaoId}/unidades", PathItem.HttpMethod.GET, null, false, "200", new ArraySchema().items(ref("CadastroNomeOutputDTO")), "application/json", "Lista de unidades; pode estar vazia.", "400", "500");
        configurar(api, "/esqueceu-senha/{email}", PathItem.HttpMethod.POST, null, false, "200", ref("RedefinirSenhaOutputDTO"), "application/json", "Código gerado e enviado.", "403", "404", "500");
        configurar(api, "/validar-codigo", PathItem.HttpMethod.POST, "ValidarCodigoRequestDTO", true, "200", new BooleanSchema(), "application/json", "Resultado da validação do código.", "400", "403", "500");
        configurar(api, "/redefinir-senha", PathItem.HttpMethod.POST, "RedefinirSenhaRequestDTO", true, "200", new StringSchema().example("Nova senha registrada com sucesso!"), "text/plain", "Senha alterada.", "400", "403", "404", "500");

        Operation login = operacao(api, "/login", PathItem.HttpMethod.POST);

        if (login != null) {
            login.getResponses().get("500").setDescription(
                    "Falha de autenticação ou de dependência; atualmente credenciais inválidas e usuário inativo podem retornar 500.");
        }

        Operation google = operacao(api, "/login/google", PathItem.HttpMethod.POST);
        if (google != null) {
            Map<String, Object> autenticado = new LinkedHashMap<>();

            autenticado.put("status", "AUTHENTICATED");
            autenticado.put("email", "pessoa@escola.com");
            autenticado.put("nome", "Pessoa Exemplo");
            autenticado.put("token", "access-token");
            autenticado.put("refreshToken", "refresh-token");
            Map<String, Object> semCadastro = new LinkedHashMap<>(autenticado);
            semCadastro.put("status", "REGISTRATION_REQUIRED");
            semCadastro.put("token", null);
            semCadastro.put("refreshToken", null);

            google.getResponses().get("200").getContent().get("application/json")
                    .addExamples("contaCadastrada", new Example().summary("Conta cadastrada").value(autenticado))
                    .addExamples("cadastroNecessario", new Example().summary("Cadastro necessário").value(semCadastro));
        }

        Operation logout = operacao(api, "/logout", PathItem.HttpMethod.POST);

        if (logout != null) {
            Schema<?> corpoOpcional = new ObjectSchema().addProperty("refreshToken", new StringSchema()
                    .description("Refresh token cuja família será revogada; o campo pode ser omitido ou nulo.").nullable(true));
            logout.setRequestBody(new RequestBody().required(false)
                    .description("Corpo opcional; sem refreshToken nenhuma família é revogada.")
                    .content(new Content().addMediaType("application/json", new MediaType().schema(corpoOpcional))));
        }
    }

    private static void configurar(OpenAPI api, String path, PathItem.HttpMethod method, String requestSchema,
                                   boolean requestRequired, String successCode, Schema<?> successSchema,
                                   String contentType, String successDescription, String... errors) {
        Operation operation = operacao(api, path, method);
        if (operation == null) return;
        operation.setSecurity(List.of());
        if (requestSchema != null) {
            operation.setRequestBody(new RequestBody().required(requestRequired)
                    .content(new Content().addMediaType("application/json", new MediaType().schema(ref(requestSchema)))));
        }
        ApiResponse success = new ApiResponse().description(successDescription);
        if (successSchema != null)
            success.setContent(new Content().addMediaType(contentType, new MediaType().schema(successSchema)));
        ApiResponses responses = new ApiResponses();
        responses.addApiResponse(successCode, success);
        for (String error : errors) responses.addApiResponse(error, erro(error));
        operation.setResponses(responses);
    }

    private static Schema<?> ref(String name) {
        return new Schema<>().$ref("#/components/schemas/" + name);
    }

    private static ApiResponse erro(String status) {
        String description = switch (status) {
            case "400" -> "Corpo ou parâmetros inválidos.";
            case "401" -> "Refresh token inválido, expirado ou revogado.";
            case "403" -> "Regra de negócio ou código de recuperação inválido.";
            case "404" -> "Usuário ou unidade não encontrado.";
            default -> "Falha ao processar a operação ou em serviço dependente.";
        };
        Schema<?> mensagem = new ComposedSchema().addOneOfItem(new StringSchema())
                .addOneOfItem(new ArraySchema().items(new StringSchema()));
        return new ApiResponse().description(description).content(new Content().addMediaType("application/json",
                new MediaType().schema(new ObjectSchema().addProperty("message", mensagem))));
    }

    private static Operation operacao(OpenAPI api, String path, PathItem.HttpMethod method) {
        if (api.getPaths() == null) return null;
        PathItem item = api.getPaths().get("/api/v1/auth" + path);
        return item == null ? null : item.readOperationsMap().get(method);
    }
}
