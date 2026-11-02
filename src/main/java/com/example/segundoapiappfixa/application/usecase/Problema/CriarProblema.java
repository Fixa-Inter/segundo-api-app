package com.example.segundoapiappfixa.application.usecase.Problema;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.repository.*;
import com.example.segundoapiappfixa.application.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.domain.enums.TipoNotificacao;
import com.example.segundoapiappfixa.domain.service.NotificacaoSender;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class CriarProblema {

    private final ProblemaRepository problemaRepository;
    private final LocalUnidadeRepository localUnidadeRepository;
    private final CategoriaEquipamentoRepository categoriaEquipamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final NotificacaoSender notificacoes;

    @Transactional
    public Problema cadastrar(Problema problema, List<String> fotos, Long usuarioId) {
        if (problema == null) {
            throw new EntidadeNaoEncontradaException("validation.problema.required");
        }

        Long localUnidadeId = problema.getLocalUnidade() == null
                ? null : problema.getLocalUnidade().getId();

        Long categoriaEquipamentoId = problema.getCategoriaEquipamento() == null
                ? null : problema.getCategoriaEquipamento().getId();

        LocalUnidade localUnidade = localUnidadeRepository.findById(localUnidadeId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("validation.local.required"));

        CategoriaEquipamento categoriaEquipamento = categoriaEquipamentoRepository.findById(categoriaEquipamentoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("validation.categoria.required"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("validation.usuario.required"));

        problema.setUsuario(usuario);
        problema.setLocalUnidade(localUnidade);
        problema.setCategoriaEquipamento(categoriaEquipamento);

        Long problemaId = problemaRepository.save(problema, fotos);

        Problema criado = problemaRepository.findById(problemaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Problema cadastrado não foi encontrado."));

        String mensagem = "Nova solicitação registrada: " + problema.getTitulo() + ".";
        usuarioRepository.findGestoresAtivosByUnidadeId(localUnidade.getUnidade().getId())
                .forEach(gestor -> notificacoes.notificarAposPersistencia(
                        gestor.getId(),
                        TipoNotificacao.SOLICITACAO.getId(),
                        problemaId,
                        mensagem
                ));

        return criado;
    }
}
