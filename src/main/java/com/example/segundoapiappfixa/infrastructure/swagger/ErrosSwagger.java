package com.example.segundoapiappfixa.infrastructure.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.ComposedSchema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

import java.util.List;
import java.util.Map;

/** Formatos produzidos pelo tratamento global e pela camada de segurança. */
final class ErrosSwagger {
    private ErrosSwagger() {}

    static void complementar(OpenAPI api) {
        if (api.getPaths() == null) return;
        api.getPaths().forEach((path, item) -> {
            if (!path.startsWith("/api/v1/")) return;
            boolean autenticacao = path.startsWith("/api/v1/auth/");
            item.readOperations().forEach(operation -> {
                if (operation.getResponses() == null) operation.setResponses(new ApiResponses());
                ApiResponses responses = operation.getResponses();
                if (!autenticacao) {
                    responses.putIfAbsent("400", new ApiResponse().description("Dados ou parâmetros inválidos."));
                    responses.putIfAbsent("401", new ApiResponse().description("Autenticação ausente ou inválida; a resposta pode não ter corpo."));
                    responses.putIfAbsent("403", new ApiResponse().description("Acesso negado ou regra de negócio impedindo a operação."));
                    responses.putIfAbsent("404", new ApiResponse().description("Registro não encontrado."));
                    responses.putIfAbsent("409", new ApiResponse().description("Conflito de integridade ou atualização concorrente."));
                    responses.putIfAbsent("423", new ApiResponse().description("Não foi possível obter o bloqueio no banco de dados."));
                    responses.putIfAbsent("500", new ApiResponse().description("Falha ao processar a operação."));
                }
                for (String status : List.of("400", "401", "403", "404", "409", "423", "500")) {
                    ApiResponse response = responses.get(status);
                    if (response == null || (status.equals("401") && !autenticacao)) continue;
                    response.setContent(conteudo(status, !autenticacao));
                }
            });
        });
    }

    private static Content conteudo(String status, boolean protegido) {
        Schema<?> mensagem = new ComposedSchema().addOneOfItem(new StringSchema())
                .addOneOfItem(new ArraySchema().items(new StringSchema()));
        Schema<?> corpo = new ObjectSchema().addProperty("message", mensagem).addRequiredItem("message");
        if (status.equals("403") && protegido) {
            corpo = new ComposedSchema().addOneOfItem(corpo)
                    .addOneOfItem(new ObjectSchema().addProperty("error", new StringSchema()).addRequiredItem("error"));
        }
        MediaType json = new MediaType().schema(corpo);
        String texto = switch (status) {
            case "400" -> "Dados da requisição inválidos.";
            case "401" -> "Refresh token inválido, expirado ou revogado.";
            case "403" -> "Operação não permitida.";
            case "404" -> "Registro não encontrado.";
            case "409" -> "O registro foi alterado por outra operação. Tente novamente.";
            case "423" -> "O banco de dados está ocupado. Tente novamente em instantes.";
            default -> "Não foi possível processar a requisição.";
        };
        json.addExamples("mensagem", new Example().summary("Erro retornado pela API").value(Map.of("message", texto)));
        if (status.equals("400")) {
            json.addExamples("validacao", new Example().summary("Erros de validação de campos")
                    .value(Map.of("message", List.of("Campo obrigatório."))));
        }
        if (status.equals("403") && protegido) {
            json.addExamples("acessoNegado", new Example().summary("Acesso negado pela segurança")
                    .value(Map.of("error", "Você não tem permissão para acessar este recurso.")));
        }
        return new Content().addMediaType("application/json", json);
    }
}
