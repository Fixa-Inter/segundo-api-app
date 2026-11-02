package com.example.segundoapiappfixa.application.usecase.Evento;

import com.example.segundoapiappfixa.domain.model.input.Evento.EventoCadastrarDados;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.application.security.UnidadeAcesso;
import com.example.segundoapiappfixa.domain.model.Evento;
import com.example.segundoapiappfixa.domain.model.LocalUnidade;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.enums.TipoNotificacao;
import com.example.segundoapiappfixa.domain.service.NotificacaoSender;
import com.example.segundoapiappfixa.domain.repository.EventoRepository;
import com.example.segundoapiappfixa.domain.repository.LocalUnidadeRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.application.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@UseCase
@RequiredArgsConstructor
public class CadastrarEvento {
    private final EventoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final LocalUnidadeRepository localRepository;
    private final NotificacaoSender notificacoes;

    @Transactional
    public Evento cadastrar(EventoCadastrarDados dto, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        LocalUnidade local = localRepository.findById(dto.localUnidadeId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.local.required"));

        UnidadeAcesso.validarLocal(usuario, local);

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

        Evento criado = repository.save(evento);

        String mensagem = "Novo evento: " + criado.getTitulo() + ".";
        usuarioRepository.findIdsAtivosByUnidadeId(local.getUnidade().getId())
                .forEach(id -> notificacoes.notificarAposPersistencia(
                        id,
                        TipoNotificacao.EVENTO.getId(),
                        criado.getId(),
                        mensagem
                ));

        return criado;
    }
}
