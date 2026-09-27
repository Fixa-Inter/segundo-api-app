package com.example.segundoapiappfixa.infrastructure.swagger;

import com.example.segundoapiappfixa.adapters.controller.contract.EventoControllerContract;
import com.example.segundoapiappfixa.adapters.dto.input.Evento.EventoAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.input.Evento.EventoCadastrarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Evento.EventoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosEventosQueryParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

@Tag(
        name = "Eventos",
        description = "Gerenciamento dos eventos registrados na plataforma."
)
@SecurityRequirement(name = "bearerAuth")
public abstract class EventoSwagger implements EventoControllerContract {

    public static List<SwaggerEndpoint> endpoints() {
        return List.of(
                new SwaggerEndpoint(
                        "GET",
                        "/api/v1/eventos",
                        "Eventos",
                        "Listar eventos",
                        "Lista eventos com filtros e período de criação."
                ),
                new SwaggerEndpoint(
                        "GET",
                        "/api/v1/eventos/{eventoId}",
                        "Eventos",
                        "Consultar evento",
                        "Consulta um evento pelo identificador."
                ),
                new SwaggerEndpoint(
                        "POST",
                        "/api/v1/eventos",
                        "Eventos",
                        "Cadastrar evento",
                        "Cria um novo evento."
                ),
                new SwaggerEndpoint(
                        "PATCH",
                        "/api/v1/eventos",
                        "Eventos",
                        "Atualizar evento",
                        "Atualiza um evento."
                ),
                new SwaggerEndpoint(
                        "DELETE",
                        "/api/v1/eventos/{eventoId}",
                        "Eventos",
                        "Remover evento",
                        "Remove um evento."
                )
        );
    }

    @Override
    @Operation(
            summary = "Listar eventos",
            description = "Lista eventos com filtros e período de criação."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Eventos encontrados",
                    content = @Content(
                            schema = @Schema(implementation = EventoOutputDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    public abstract ResponseEntity<List<EventoOutputDTO>> listar(
            @Parameter(
                    description = "Campos de saída separados por vírgula",
                    in = ParameterIn.QUERY,
                    example = "id,titulo,dataCriacao"
            )
            String campos,

            FiltrosEventosQueryParam filtros,
            Authentication authentication
    );

    @Override
    @Operation(
            summary = "Consultar evento",
            description = "Consulta um evento pelo identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Evento encontrado",
                    content = @Content(
                            schema = @Schema(implementation = EventoOutputDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    public abstract ResponseEntity<EventoOutputDTO> listarDetalhes(
            @Parameter(
                    description = "Identificador do evento",
                    required = true,
                    example = "1"
            )
            Long eventoId,

            @Parameter(
                    description = "Campos de saída separados por vírgula",
                    in = ParameterIn.QUERY,
                    example = "id,titulo,dataCriacao"
            )
            String campos,

            Authentication authentication
    );

    @Override
    @Operation(
            summary = "Cadastrar evento",
            description = "Cria um novo evento."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Evento criado",
                    content = @Content(
                            schema = @Schema(implementation = EventoOutputDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    public abstract ResponseEntity<EventoOutputDTO> cadastrar(
            @RequestBody(
                    description = "Dados do evento",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = EventoCadastrarInputDTO.class)
                    )
            )
            EventoCadastrarInputDTO dto,

            Authentication authentication
    );

    @Override
    @Operation(
            summary = "Atualizar evento",
            description = "Atualiza um evento."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Evento atualizado",
                    content = @Content(
                            schema = @Schema(implementation = EventoOutputDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    public abstract ResponseEntity<EventoOutputDTO> atualizar(
            @RequestBody(
                    description = "Dados do evento",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = EventoAtualizarInputDTO.class)
                    )
            )
            EventoAtualizarInputDTO dto,

            Authentication authentication
    );

    @Override
    @Operation(
            summary = "Remover evento",
            description = "Remove um evento."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Evento removido",
                    content = @Content(
                            schema = @Schema(implementation = EventoOutputDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    public abstract ResponseEntity<EventoOutputDTO> deletar(
            @Parameter(
                    description = "Identificador do evento",
                    required = true,
                    example = "1"
            )
            Long eventoId,

            Authentication authentication
    );
}
