package com.example.segundoapiappfixa.infrastructure.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

import java.util.List;

/**
 * Operações que complementam os catálogos por recurso.
 */
final class RotasAdicionaisSwagger {
    private RotasAdicionaisSwagger() {
    }

    static List<SwaggerEndpoint> endpoints() {
        return List.of(
                new SwaggerEndpoint("GET", "/api/v1/local-unidade", "Locais da unidade", "Listar locais", "Lista os locais da unidade do usuário autenticado."),
                new SwaggerEndpoint("GET", "/api/v1/notificacao", "Notificações", "Listar notificações", "Lista as notificações do usuário autenticado."),
                new SwaggerEndpoint("PATCH", "/api/v1/notificacao/{notificacaoId}/visualizada", "Notificações", "Marcar como visualizada", "Marca uma notificação do usuário como visualizada."),
                new SwaggerEndpoint("DELETE", "/api/v1/notificacao/{notificacaoId}", "Notificações", "Excluir notificação", "Exclui uma notificação do usuário autenticado."),
                new SwaggerEndpoint("POST", "/api/v1/notificacao/dispositivo", "Notificações", "Registrar dispositivo", "Registra o token FCM do dispositivo do usuário autenticado."),
                new SwaggerEndpoint("DELETE", "/api/v1/notificacao/dispositivo", "Notificações", "Remover dispositivo", "Remove o token FCM informado no parâmetro token."),
                new SwaggerEndpoint("GET", "/api/v1/observacoes-conclusao", "Observações de conclusão", "Listar observações", "Lista observações de conclusão com filtros e seleção de campos."),
                new SwaggerEndpoint("GET", "/api/v1/observacoes-conclusao/minhas", "Observações de conclusão", "Listar minhas observações", "Lista observações de conclusão do usuário com filtros e seleção de campos."),
                new SwaggerEndpoint("GET", "/api/v1/observacoes-conclusao/{id}", "Observações de conclusão", "Consultar observação", "Consulta uma observação de conclusão pelo identificador."),
                new SwaggerEndpoint("POST", "/api/v1/observacoes-conclusao", "Observações de conclusão", "Cadastrar observação", "Registra a observação de conclusão de uma ordem de serviço."),
                new SwaggerEndpoint("PATCH", "/api/v1/observacoes-conclusao", "Observações de conclusão", "Atualizar observação", "Atualiza uma observação de conclusão."),
                new SwaggerEndpoint("DELETE", "/api/v1/observacoes-conclusao/{id}", "Observações de conclusão", "Excluir observação", "Exclui uma observação e retorna o registro removido."),
                new SwaggerEndpoint("PATCH", "/api/v1/perfil", "Perfil", "Atualizar perfil", "Atualiza os dados do perfil autenticado."),
                new SwaggerEndpoint("PATCH", "/api/v1/solicitacoes", "Problemas", "Atualizar solicitação", "Atualiza os dados de uma solicitação."),
                new SwaggerEndpoint("DELETE", "/api/v1/solicitacoes/{problemaId}", "Problemas", "Excluir solicitação", "Exclui a solicitação indicada e retorna o registro removido."),
                new SwaggerEndpoint("GET", "/api/v1/tecnicos/recomendar/{categoriaProblema}", "Técnicos", "Recomendar técnicos", "Lista os técnicos recomendados para a categoria de problema informada.")
        );
    }

    static void complementar(OpenAPI api) {
        for (var entry : api.getPaths().entrySet()) {
            if (entry.getKey().startsWith("/api/v1/auth")) continue;

            PathItem path = entry.getValue();
            for (var operationEntry : path.readOperationsMap().entrySet()) {
                Operation operation = operationEntry.getValue();
                if (operation.getResponses() == null) operation.setResponses(new ApiResponses());

                ApiResponses responses = operation.getResponses();
                boolean noContent = entry.getKey().equals("/api/v1/notificacao/dispositivo")
                        || (entry.getKey().equals("/api/v1/notificacao/{notificacaoId}") && operationEntry.getKey() == PathItem.HttpMethod.DELETE);

                boolean created = operationEntry.getKey() == PathItem.HttpMethod.POST && !noContent;

                if (noContent) {
                    responses.remove("200");
                    responses.addApiResponse("204", new ApiResponse().description("Operação concluída sem corpo de resposta."));

                } else if (created) {
                    ApiResponse success = responses.remove("200");
                    if (success != null) responses.addApiResponse("201", success.description("Registro criado."));
                }
            }
        }
    }
}
