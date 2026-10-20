package com.example.segundoapiappfixa.application.usecase.Perfil;

import com.example.segundoapiappfixa.adapters.dto.input.Usuario.UsuarioAtualizarInputDTO;
import com.example.segundoapiappfixa.adapters.mapper.UsuarioMapper;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.TipoFoto;
import com.example.segundoapiappfixa.domain.model.Foto;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.FotoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@UseCase
@RequiredArgsConstructor
public class AtualizarPerfil {

    private final UsuarioRepository usuarioRepository;
    private final FotoRepository fotoRepository;

    public Usuario atualizar(UsuarioAtualizarInputDTO dto, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("validation.usuario.required"));

        if (dto.nomeCompleto() != null) usuario.setNomeCompleto(dto.nomeCompleto());
        if (dto.cargo() != null) usuario.setCargo(dto.cargo());

        if (dto.urlFotoPerfil() != null) {
            Foto foto = new Foto(
                    null,
                    null,
                    null,
                    null,
                    null,
                    usuario,
                    dto.urlFotoPerfil(),
                    true,
                    LocalDateTime.now()
            );

            fotoRepository.save(foto, TipoFoto.USUARIO);
            usuario.setUrlFotoPerfil(dto.urlFotoPerfil());
        }

        return usuarioRepository.save(usuario);
    }

}
