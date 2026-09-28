package com.example.segundoapiappfixa.infrastructure.swagger;

import com.example.segundoapiappfixa.adapters.controller.contract.TecnicoControllerContract;
import com.example.segundoapiappfixa.adapters.dto.input.Aptidao.AptidaoAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Aptidao.AptidaoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.OrdemServico.OrdemServicoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Usuario.UsuarioOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosUsuarioQueryParam;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

@Tag(name = "Técnicos", description = "Consulta de técnicos, competências e ordens de serviço.")
@SecurityRequirement(name = "bearerAuth")
public abstract class TecnicoSwagger implements TecnicoControllerContract {
    public static List<SwaggerEndpoint> endpoints() {
        return List.of(new SwaggerEndpoint("GET", "/api/v1/tecnicos", "Técnicos", "Listar técnicos", "Lista técnicos com filtros."),
                new SwaggerEndpoint("GET", "/api/v1/tecnicos/competencias/{usuarioId}", "Técnicos", "Listar competências", "Lista competências de um técnico."),
                new SwaggerEndpoint("GET", "/api/v1/tecnicos/os/{usuarioId}", "Técnicos", "Listar ordens do técnico", "Lista ordens de um técnico."),
                new SwaggerEndpoint("PATCH", "/api/v1/tecnicos/competencias", "Técnicos", "Atualizar competência", "Atualiza uma competência."));
    }

    @Operation(summary = "Listar técnicos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Técnicos encontrados", content = @Content(schema = @Schema(implementation = UsuarioOutputDTO.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public abstract ResponseEntity<List<UsuarioOutputDTO>> listar(@Parameter(description = "Campos separados por vírgula", in = ParameterIn.QUERY) String campos, FiltrosUsuarioQueryParam filtros, Authentication authentication);

    @Operation(summary = "Listar competências")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Competências encontradas", content = @Content(schema = @Schema(implementation = AptidaoOutputDTO.class))),
            @ApiResponse(responseCode = "404", description = "Técnico não encontrado")
    })
    public abstract ResponseEntity<List<AptidaoOutputDTO>> listarCompetencias(@Parameter(required = true, description = "Identificador do técnico") Long usuarioId, @Parameter(description = "Campos separados por vírgula", in = ParameterIn.QUERY) String campos, Authentication authentication);

    @Operation(summary = "Listar ordens do técnico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ordens encontradas", content = @Content(schema = @Schema(implementation = OrdemServicoOutputDTO.class))),
            @ApiResponse(responseCode = "404", description = "Técnico não encontrado")
    })
    public abstract ResponseEntity<List<OrdemServicoOutputDTO>> listarOrdensServico(@Parameter(required = true, description = "Identificador do técnico") Long usuarioId, @Parameter(description = "Campos separados por vírgula", in = ParameterIn.QUERY) String campos, Authentication authentication);

    @Operation(summary = "Atualizar competência")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Competência atualizada", content = @Content(schema = @Schema(implementation = AptidaoOutputDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public abstract ResponseEntity<AptidaoOutputDTO> atualizarAptidao(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados da competência", required = true) AptidaoAtualizarInputDTO dto, Authentication authentication);
}


