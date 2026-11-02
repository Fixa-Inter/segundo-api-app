package com.example.segundoapiappfixa.application.usecase.ObservacaoConclusao;

import com.example.segundoapiappfixa.domain.model.ObservacaoConclusao;
import com.example.segundoapiappfixa.domain.enums.Dificuldade;
import com.example.segundoapiappfixa.domain.repository.*;
import com.example.segundoapiappfixa.application.exception.RegraProblemaException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CadastrarObservacaoConclusaoTest {
    @Mock private ObservacaoConclusaoRepository observacaoRepository;
    @Mock private OrdemServicoRepository ordemServicoRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private FotoRepository fotoRepository;
    @InjectMocks private CadastrarObservacaoConclusao cadastrar;

    @Test
    void deveRecusarObservacaoEmBrancoAntesDeAcessarPersistencia() {
        ObservacaoConclusao observacao = new ObservacaoConclusao();
        observacao.setDificuldade(Dificuldade.FACIL);
        observacao.setObservacao("   ");

        RegraProblemaException exception = assertThrows(RegraProblemaException.class,
                () -> cadastrar.cadastrar(observacao, List.of("foto.jpg"), 10L, com.example.segundoapiappfixa.domain.enums.TipoAcesso.TECNICO));

        assertEquals("validation.observacaoConclusao.observacao.invalid", exception.getMessage());
        verify(usuarioRepository, never()).findById(10L);
    }
}
