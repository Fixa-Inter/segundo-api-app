package com.example.segundoapiappfixa.infrastructure.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import java.util.List;

/** Metadados de autenticação mantidos fora dos contratos de transporte. */
final class AuthSchemaSwagger {
    private AuthSchemaSwagger() {}

    static void complementar(OpenAPI api) {
        if (api.getComponents() == null || api.getComponents().getSchemas() == null) return;
        campo(api, "CadastroNomeOutputDTO", "id", "Identificador da instituição ou unidade", "1", false, false);
        campo(api, "CadastroNomeOutputDTO", "nome", "Nome para exibição", "Unidade Centro", false, false);
        campo(api, "CadastroUsuarioRequestDTO", "nomeCompleto", "Nome completo do novo solicitante", "Ana Silva", false, false);
        campo(api, "CadastroUsuarioRequestDTO", "email", "E-mail compatível com o domínio da instituição da unidade", "ana@escola.com", false, false);
        campo(api, "CadastroUsuarioRequestDTO", "senha", "Senha com pelo menos quatro caracteres", "senha-exemplo", true, false);
        campo(api, "CadastroUsuarioRequestDTO", "unidadeId", "Identificador da unidade escolhida no cadastro", "1", false, false);
        campo(api, "CadastroUsuarioRequestDTO", "cargo", "Cargo do usuário", "Professor", false, false);
        campo(api, "CadastroUsuarioRequestDTO", "dataNascimento", "Data de nascimento; o cadastro exige pelo menos 12 anos", "2000-05-10", false, false);
        campo(api, "GoogleLoginRequestDTO", "idToken", "ID token Firebase obtido após autenticação Google", null, true, false);
        campo(api, "GoogleLoginResponseDTO", "status", "AUTHENTICATED quando a conta existe; REGISTRATION_REQUIRED quando ainda não há cadastro", null, false, false);
        valores(api, "GoogleLoginResponseDTO", "status", List.of("AUTHENTICATED", "REGISTRATION_REQUIRED"));
        campo(api, "GoogleLoginResponseDTO", "email", "E-mail verificado da conta", null, false, false);
        campo(api, "GoogleLoginResponseDTO", "nome", "Nome do usuário retornado pelo cadastro ou pelo Google", null, false, true);
        campo(api, "GoogleLoginResponseDTO", "token", "Access token, presente quando a conta está cadastrada", null, false, true);
        campo(api, "GoogleLoginResponseDTO", "refreshToken", "Refresh token, presente quando a conta está cadastrada", null, false, true);
        campo(api, "LoginRequestDTO", "email", "E-mail utilizado no cadastro", "pessoa@escola.com", false, false);
        campo(api, "LoginRequestDTO", "senha", "Senha da conta", "senha-exemplo", true, false);
        campo(api, "LoginResponseDTO", "email", "E-mail do usuário autenticado", "pessoa@escola.com", false, false);
        campo(api, "LoginResponseDTO", "token", "Access token JWT usado como Bearer nas rotas protegidas", null, false, false);
        campo(api, "LoginResponseDTO", "refreshToken", "Refresh token usado para renovar a sessão", null, false, false);
        campo(api, "RedefinirSenhaOutputDTO", "nome", "Primeiro nome do usuário", "Ana", false, false);
        campo(api, "RedefinirSenhaOutputDTO", "email", "E-mail utilizado na solicitação", "ana@escola.com", false, false);
        campo(api, "RedefinirSenhaOutputDTO", "mensagem", "Confirmação de envio", "Código enviado para o e-mail.", false, false);
        campo(api, "RedefinirSenhaRequestDTO", "email", "E-mail da conta", "pessoa@escola.com", false, false);
        campo(api, "RedefinirSenhaRequestDTO", "codigo", "Código de recuperação recebido por e-mail", "123456", false, false);
        campo(api, "RedefinirSenhaRequestDTO", "novaSenha", "Nova senha", "nova-senha", true, false);
        campo(api, "RefreshTokenRequestDTO", "refreshToken", "Refresh token da sessão", null, true, false);
        campo(api, "RefreshTokenResponseDTO", "token", "Novo access token JWT", null, false, false);
        campo(api, "RefreshTokenResponseDTO", "refreshToken", "Novo refresh token; substitui o token usado na renovação", null, false, false);
        campo(api, "ValidarCodigoRequestDTO", "email", "E-mail da conta", "pessoa@escola.com", false, false);
        campo(api, "ValidarCodigoRequestDTO", "codigo", "Código recebido por e-mail", "123456", false, false);
    }

    private static Schema<?> propriedade(OpenAPI api, String nome, String campo) {
        Schema<?> schema = api.getComponents().getSchemas().get(nome);
        if (schema == null || schema.getProperties() == null) return null;
        return (Schema<?>) schema.getProperties().get(campo);
    }

    private static void campo(OpenAPI api, String nome, String campo, String descricao, String exemplo, boolean escrita, boolean anulavel) {
        Schema<?> propriedade = propriedade(api, nome, campo);
        if (propriedade == null) return;
        propriedade.setDescription(descricao);
        if (exemplo != null) {
            propriedade.setExample("integer".equals(propriedade.getType()) ? Long.valueOf(exemplo) : exemplo);
        }
        if (escrita) propriedade.setWriteOnly(true);
        if (anulavel) propriedade.setNullable(true);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void valores(OpenAPI api, String nome, String campo, List<String> valores) {
        Schema propriedade = propriedade(api, nome, campo);
        if (propriedade != null) propriedade.setEnum(valores);
    }
}
