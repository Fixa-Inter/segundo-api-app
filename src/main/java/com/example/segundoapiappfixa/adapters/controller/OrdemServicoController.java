package com.example.segundoapiappfixa.adapters.controller;

import com.example.segundoapiappfixa.adapters.dto.output.OrdemServico.OrdemServicoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosOrdemServicoQueryParam;
import com.example.segundoapiappfixa.adapters.controller.contract.OrdemServicoControllerContract;
import com.example.segundoapiappfixa.adapters.mapper.OrdemServicoMapper;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.DynamicFieldFilter;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.OrdemServicoDynamicMapper;
import com.example.segundoapiappfixa.adapters.utils.ControllerUtils;
import com.example.segundoapiappfixa.application.usecase.OrdemServico.DeletarOrdemServico;
import com.example.segundoapiappfixa.application.usecase.OrdemServico.AtualizarOrdemServico;
import com.example.segundoapiappfixa.application.usecase.OrdemServico.CadastrarOrdemServico;
import com.example.segundoapiappfixa.application.usecase.OrdemServico.ListarOrdensServico;
import com.example.segundoapiappfixa.adapters.dto.input.OrdemServico.OrdemServicoAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.input.OrdemServico.OrdemServicoCadastrarInputDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import com.example.segundoapiappfixa.auth.dto.AuthenticatedUser;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/os")
@RequiredArgsConstructor
public class OrdemServicoController implements OrdemServicoControllerContract {

    // UseCases
    private final ListarOrdensServico listarOrdensServico;
    private final CadastrarOrdemServico cadastrarOrdemServico;
    private final AtualizarOrdemServico atualizarOrdemServico;
    private final DeletarOrdemServico deletarOrdemServico;

    // Mappers
    private final OrdemServicoMapper mapper;
    private final OrdemServicoDynamicMapper dynamicMapper;

    // GET
    @GetMapping
    public ResponseEntity<List<OrdemServicoOutputDTO>> listar(
            @RequestParam(required = false)
            String campos,

            @ModelAttribute
            FiltrosOrdemServicoQueryParam filtros,

            Authentication authentication
    ) {
       return ResponseEntity.ok(
                mask(toOutputDTO(listarOrdensServico.listar(
                        ControllerUtils.usuarioId(authentication), filtros
                )), campos)
        );
    }

    @GetMapping("/minhas")
    public ResponseEntity<List<OrdemServicoOutputDTO>> listarMinhas(
            @RequestParam(required = false)
            String campos,

            @ModelAttribute
            FiltrosOrdemServicoQueryParam filtros,

            Authentication authentication
    ) {
        return ResponseEntity.ok(
                mask(toOutputDTO(listarOrdensServico.listarMinhas(
                        ControllerUtils.usuarioId(authentication), filtros
                )), campos)
        );
    }

    @GetMapping("/{ordemServicoId}")
    public ResponseEntity<OrdemServicoOutputDTO> listarDetalhes(
            @PathVariable
            Long ordemServicoId,

            @RequestParam(required = false)
            String campos,

            Authentication authentication
    ) {
        OrdemServicoOutputDTO dto = mapper.toOutputDTO(listarOrdensServico.listar(
                ordemServicoId,
                ControllerUtils.usuarioId(authentication),
                ControllerUtils.isGestor(authentication)
        ));

        return ResponseEntity.ok(mask(dto, campos));
    }

    // POST
    @PostMapping
    public ResponseEntity<OrdemServicoOutputDTO> cadastrar(
            @Valid
            @RequestBody
            OrdemServicoCadastrarInputDTO input,

            Authentication authentication
    ) {
       return ResponseEntity.status(HttpStatus.CREATED).body(
                mapper.toOutputDTO(cadastrarOrdemServico.cadastrar(
                        input,
                        ControllerUtils.usuarioId(authentication)
                ))
        );
    }

    // PATCH
    @PatchMapping
    public ResponseEntity<OrdemServicoOutputDTO> atualizar(
            @Valid
            @RequestBody
            OrdemServicoAtualizarInputDTO input,

            Authentication authentication
    ) {
       return ResponseEntity.ok(mapper.toOutputDTO(atualizarOrdemServico.atualizar(
                input,
                ControllerUtils.usuarioId(authentication),
                ControllerUtils.isGestor(authentication)
        )));
    }

    // DELETE
    @DeleteMapping("/{ordemServicoId}")
    public ResponseEntity<OrdemServicoOutputDTO> deletarOrdemServico(
            @PathVariable
            Long ordemServicoId,

            Authentication authentication
    ) {
        return ResponseEntity.ok(
                mapper.toOutputDTO(deletarOrdemServico.deletar(
                        ordemServicoId,
                        ControllerUtils.isGestor(authentication),
                        ControllerUtils.usuarioId(authentication)
                ))
        );

    }

    // Mapper para DTO de saída em lote
    private List<OrdemServicoOutputDTO> toOutputDTO(List<OrdemServico> ordemServicos) {
        return ordemServicos.stream().map(mapper::toOutputDTO).toList();
    }

    // Aplicação do Mapper Dinâmico
    private List<OrdemServicoOutputDTO> mask(List<OrdemServicoOutputDTO> dtos, String campos) {
        if (dtos.isEmpty()) return List.of();

        List<String> available = DynamicFieldFilter.availableFields(dtos.getFirst());
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dtos
                .stream()
                .map(dto -> dynamicMapper.mask(dto, selected))
                .toList();
    }

    private OrdemServicoOutputDTO mask(OrdemServicoOutputDTO dto, String campos) {
        List<String> available = DynamicFieldFilter.availableFields(dto);
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dynamicMapper.mask(dto, selected);
    }
}
