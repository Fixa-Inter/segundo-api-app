package com.example.segundoapiappfixa.adapters.controller;

import com.example.segundoapiappfixa.adapters.dto.output.Usuario.UsuarioOutputDTO;
import com.example.segundoapiappfixa.adapters.mapper.UsuarioMapper;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.DynamicFieldFilter;
import com.example.segundoapiappfixa.adapters.mapper.dynamic.UsuarioDynamicMapper;
import com.example.segundoapiappfixa.adapters.utils.ControllerUtils;
import com.example.segundoapiappfixa.application.usecase.Perfil.DetalhesPerfil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/perfil")
@RequiredArgsConstructor
public class PerfilController {

    // UseCases
    private final DetalhesPerfil detalhesPerfil;

    // Mappers
    private final UsuarioMapper mapper;
    private final UsuarioDynamicMapper dynamicMapper;

    // GET
    @GetMapping
    public ResponseEntity<UsuarioOutputDTO> detalhesPerfil(
            @RequestParam(required = false)
            String campos,

            Authentication authentication
    ) {
         return ResponseEntity.ok(mask(
                 mapper.toOutputDTO(
                         detalhesPerfil.detalhesPerfil(ControllerUtils.usuarioId(authentication))
                 ),
                 campos
         ));
    }

    // Aplicação do Mapper Dinâmico
    private UsuarioOutputDTO mask(UsuarioOutputDTO dto, String campos) {
        if (dto == null) return null;

        List<String> available = DynamicFieldFilter.availableFields(dto);
        List<String> selected = DynamicFieldFilter.selectedFields(campos, available);

        return dynamicMapper.mask(dto, selected);
    }

}
