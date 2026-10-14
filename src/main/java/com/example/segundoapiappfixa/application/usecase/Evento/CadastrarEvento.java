package com.example.segundoapiappfixa.application.usecase.Evento;

import com.example.segundoapiappfixa.adapters.dto.input.Evento.EventoCadastrarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Evento;
import com.example.segundoapiappfixa.domain.model.LocalUnidade;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.EventoRepository;
import com.example.segundoapiappfixa.domain.repository.LocalUnidadeRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@UseCase
@RequiredArgsConstructor
public class CadastrarEvento {
    private final EventoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final LocalUnidadeRepository localRepository;

    public Evento cadastrar(EventoCadastrarInputDTO dto, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        LocalUnidade local = localRepository.findById(dto.localUnidadeId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.local.required"));

        Evento evento = new Evento(
                null,
                usuario,
                local,
                dto.titulo(),
                dto.descricao(),
                dto.descricaoLocal(),
                dto.observacao(),
                dto.dataHoraInicio(),
                dto.dataHoraFim(),
                LocalDateTime.now()
        );

        return repository.save(evento);
    }
}
