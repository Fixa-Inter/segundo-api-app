package com.example.segundoapiappfixa.adapters.controller;

import com.example.segundoapiappfixa.adapters.dto.input.Evento.EventoAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.input.Evento.EventoCadastrarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Evento.EventoOutputDTO;
import com.example.segundoapiappfixa.adapters.mapper.EventoMapper;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.DynamicFieldFilter;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.EventoDynamicMapper;
import com.example.segundoapiappfixa.adapters.utils.ControllerUtils;
import com.example.segundoapiappfixa.application.usecase.Evento.AtualizarEvento;
import com.example.segundoapiappfixa.application.usecase.Evento.CadastrarEvento;
import com.example.segundoapiappfixa.application.usecase.Evento.DeletarEvento;
import com.example.segundoapiappfixa.application.usecase.Evento.ListarEventos;
import com.example.segundoapiappfixa.domain.model.Evento;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/eventos")
@RequiredArgsConstructor
public class EventoController {

    // UseCases
    private final ListarEventos listarEventos;
    private final CadastrarEvento cadastrarEvento;
    private final AtualizarEvento atualizarEvento;
    private final DeletarEvento deletarEvento;

    // Mappers
    private final EventoMapper mapper;
    private final EventoDynamicMapper dynamicMapper;

    // GET
    @GetMapping
    public ResponseEntity<List<EventoOutputDTO>> listar(
            @RequestParam(required = false) String campos,
            Authentication authentication
    ) {
        return ResponseEntity.ok(mask(
                toOutputDTO(listarEventos.listar(ControllerUtils.usuarioId(authentication))),
                campos
        ));
    }

    @GetMapping("/{eventoId}")
    public ResponseEntity<EventoOutputDTO> listarDetalhes(
            @PathVariable Long eventoId,
            @RequestParam(required = false) String campos,
            Authentication authentication
    ) {
        return ResponseEntity.ok(mask(
                mapper.toOutputDTO(listarEventos.listar(
                        eventoId,
                        ControllerUtils.usuarioId(authentication)
                )),
                campos
        ));
    }

    // POST
    @PostMapping
    public ResponseEntity<EventoOutputDTO> cadastrar(
            @Valid @RequestBody EventoCadastrarInputDTO dto,
            Authentication authentication
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toOutputDTO(
                cadastrarEvento.cadastrar(dto, ControllerUtils.usuarioId(authentication))
        ));
    }

    // PATCH
    @PatchMapping
    public ResponseEntity<EventoOutputDTO> atualizar(
            @Valid @RequestBody EventoAtualizarInputDTO dto,
            Authentication authentication
    ) {
        return ResponseEntity.ok(mapper.toOutputDTO(
                atualizarEvento.atualizar(dto, ControllerUtils.usuarioId(authentication))
        ));
    }

    // DELETE
    @DeleteMapping("/{eventoId}")
    public ResponseEntity<EventoOutputDTO> deletar(
            @PathVariable Long eventoId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(mapper.toOutputDTO(
                deletarEvento.deletar(eventoId, ControllerUtils.usuarioId(authentication))
        ));
    }

    // Mapper para DTO de saída em lote
    private List<EventoOutputDTO> toOutputDTO(List<Evento> eventos) {
        return eventos.stream().map(mapper::toOutputDTO).toList();
    }

    // Aplicação do Mapper Dinâmico
    private EventoOutputDTO mask(EventoOutputDTO dto, String campos) {
        List<String> available = DynamicFieldFilter.availableFields(dto);
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);
        return dynamicMapper.mask(dto, selected);
    }

    private List<EventoOutputDTO> mask(List<EventoOutputDTO> dtos, String campos) {
        if (dtos.isEmpty()) return List.of();
        List<String> available = DynamicFieldFilter.availableFields(dtos.getFirst());
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);
        return dtos.stream().map(dto -> dynamicMapper.mask(dto, selected)).toList();
    }
}
