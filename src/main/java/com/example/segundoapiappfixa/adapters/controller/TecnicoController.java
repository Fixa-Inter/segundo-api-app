package com.example.segundoapiappfixa.adapters.controller;

import com.example.segundoapiappfixa.adapters.dto.output.OrdemServico.OrdemServicoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Aptidao.AptidaoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.input.Aptidao.AptidaoAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Usuario.TecnicoOutputDTO;
import com.example.segundoapiappfixa.adapters.mapper.OrdemServicoMapper;
import com.example.segundoapiappfixa.adapters.mapper.AptidaoMapper;
import com.example.segundoapiappfixa.adapters.mapper.UsuarioMapper;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.DynamicFieldFilter;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.OrdemServicoDynamicMapper;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.AptidaoDynamicMapper;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.UsuarioDynamicMapper;
import com.example.segundoapiappfixa.adapters.utils.ControllerUtils;
import com.example.segundoapiappfixa.application.usecase.Tecnico.ListarTecnicos;
import com.example.segundoapiappfixa.application.usecase.Tecnico.ListarAptidoes;
import com.example.segundoapiappfixa.application.usecase.Tecnico.AtualizarAptidao;
import com.example.segundoapiappfixa.application.usecase.Tecnico.ListarOrdensServicoTecnico;
import com.example.segundoapiappfixa.domain.model.Aptidao;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.domain.model.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tecnicos")
@RequiredArgsConstructor
public class TecnicoController {

    // UseCases
    private final ListarTecnicos listarTecnicos;
    private final ListarAptidoes listarAptidoes;
    private final ListarOrdensServicoTecnico listarOrdensServicoTecnico;
    private final AtualizarAptidao atualizarAptidao;

    // Mappers
    private final UsuarioMapper usuarioMapper;
    private final AptidaoMapper aptidaoMapper;
    private final OrdemServicoMapper ordemServicoMapper;

    private final UsuarioDynamicMapper usuarioDynamicMapper;
    private final AptidaoDynamicMapper aptidaoDynamicMapper;
    private final OrdemServicoDynamicMapper ordemServicoDynamicMapper;

    // GET
    @GetMapping
    public ResponseEntity<List<TecnicoOutputDTO>> listar(
            @RequestParam(required = false)
            String campos,

            Authentication authentication
    ) {
        return ResponseEntity.ok(maskTecnicos(
                toTecnicoOutputDTO(listarTecnicos.listar(
                        ControllerUtils.usuarioId(authentication)
                )),

                campos
        ));
    }

    @GetMapping("/competencias/{usuarioId}")
    public ResponseEntity<List<AptidaoOutputDTO>> listarCompetencias(
            @PathVariable
            Long usuarioId,

            @RequestParam(required = false)
            String campos,

            Authentication authentication
    ) {
        return ResponseEntity.ok(maskAptidoes(
                toAptidaoOutputDTO(listarAptidoes.listar(
                        usuarioId,
                        ControllerUtils.usuarioId(authentication)
                )),

                campos
        ));
    }

    @GetMapping("/os/{usuarioId}")
    public ResponseEntity<List<OrdemServicoOutputDTO>> listarOrdensServico(
            @PathVariable
            Long usuarioId,

            @RequestParam(required = false)
            String campos,

            Authentication authentication
    ) {
        return ResponseEntity.ok(maskOrdensServico(
                toOrdemServicoOutputDTO(listarOrdensServicoTecnico.listar(
                        usuarioId,
                        ControllerUtils.usuarioId(authentication)
                )),

                campos
        ));
    }

    // PATCH
    @PatchMapping("/competencias")
    public ResponseEntity<AptidaoOutputDTO> atualizarAptidao(
            @Valid
            @RequestBody
            AptidaoAtualizarInputDTO dto,

            Authentication authentication
    ) {
        return ResponseEntity.ok(aptidaoMapper.toOutputDTO(
                atualizarAptidao.atualizar(
                        dto,
                        ControllerUtils.usuarioId(authentication)
                )
        ));
    }

    // Mapper para DTO de saída em lote
    private List<TecnicoOutputDTO> toTecnicoOutputDTO(List<Usuario> tecnicos) {
        return tecnicos.stream().map(usuarioMapper::toTecnicoOutputDTO).toList();
    }

    private List<AptidaoOutputDTO> toAptidaoOutputDTO(List<Aptidao> competencias) {
        return competencias.stream().map(aptidaoMapper::toOutputDTO).toList();
    }

    private List<OrdemServicoOutputDTO> toOrdemServicoOutputDTO(List<OrdemServico> ordensServico) {
        return ordensServico.stream().map(ordemServicoMapper::toOutputDTO).toList();
    }


    // Aplicação do Mapper Dinâmico
    private List<TecnicoOutputDTO> maskTecnicos(List<TecnicoOutputDTO> dtos, String campos) {
        if (dtos.isEmpty()) return List.of();

        List<String> available = DynamicFieldFilter.availableFields(dtos.getFirst());
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dtos
                .stream()
                .map(dto -> usuarioDynamicMapper.maskTecnico(dto, selected))
                .toList();
    }

    private List<AptidaoOutputDTO> maskAptidoes(List<AptidaoOutputDTO> dtos, String campos) {
        if (dtos.isEmpty()) return List.of();

        List<String> available = DynamicFieldFilter.availableFields(dtos.getFirst());
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dtos
                .stream()
                .map(dto -> aptidaoDynamicMapper.mask(dto, selected))
                .toList();
    }

    private List<OrdemServicoOutputDTO> maskOrdensServico(List<OrdemServicoOutputDTO> dtos, String campos) {
        if (dtos.isEmpty()) return List.of();

        List<String> available = DynamicFieldFilter.availableFields(dtos.getFirst());
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dtos
                .stream()
                .map(dto -> ordemServicoDynamicMapper.mask(dto, selected))
                .toList();
    }
}
