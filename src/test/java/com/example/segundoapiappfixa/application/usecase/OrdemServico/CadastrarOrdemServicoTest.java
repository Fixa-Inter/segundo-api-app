package com.example.segundoapiappfixa.application.usecase.OrdemServico;

import com.example.segundoapiappfixa.adapters.dto.input.OrdemServico.OrdemServicoCadastrarInputDTO;
import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.Prioridade;
import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import com.example.segundoapiappfixa.domain.enums.StatusOrdemServico;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.repository.OrdemServicoRepository;
import com.example.segundoapiappfixa.domain.repository.ProblemaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastrarOrdemServicoTest {

    @Mock private OrdemServicoRepository ordemServicoRepository;
    @Mock private ProblemaRepository problemaRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @InjectMocks
    private CadastrarOrdemServico cadastrarOrdemServico;

    private Usuario gestor;
    private Usuario responsavel;
    private Problema problema;

    @BeforeEach
    void setUp() {
        Endereco endereco = new Endereco();
        endereco.setId(10L);

        gestor = new Usuario();
        gestor.setId(1L);
        gestor.setTipoAcesso(TipoAcesso.GESTOR);
        gestor.setEndereco(endereco);

        responsavel = new Usuario();
        responsavel.setId(2L);
        responsavel.setTipoAcesso(TipoAcesso.TECNICO);
        responsavel.setEndereco(endereco);

        LocalEndereco local = new LocalEndereco();
        local.setEndereco(endereco);

        problema = new Problema();
        problema.setId(3L);
        problema.setStatus(StatusProblema.APROVADO);
        problema.setLocalEndereco(local);
    }

    @Test
    void deveRecusarCadastroQuandoProblemaJaPossuiOs() {
        prepararBusca();
        when(ordemServicoRepository.existsByProblemaId(3L)).thenReturn(true);

        RegraProblemaException exception = assertThrows(
                RegraProblemaException.class,
                () -> cadastrarOrdemServico.cadastrar(input(), 1L)
        );

        assertEquals("exception.ordemServico.duplicate", exception.getMessage());
        verify(ordemServicoRepository, never()).save(any());
    }

    @Test
    void deveRecusarResponsavelQueNaoEhTecnicoNemGestor() {
        prepararBusca();
        responsavel.setTipoAcesso(TipoAcesso.SOLICITANTE);

        RegraProblemaException exception = assertThrows(
                RegraProblemaException.class,
                () -> cadastrarOrdemServico.cadastrar(input(), 1L)
        );

        assertEquals("exception.responsavel.denied", exception.getMessage());
        verify(ordemServicoRepository, never()).existsByProblemaId(anyLong());
    }

    @Test
    void deveRecusarOsParaProblemaNaoAprovado() {
        prepararBusca();
        problema.setStatus(StatusProblema.PENDENTE);

        RegraProblemaException exception = assertThrows(
                RegraProblemaException.class,
                () -> cadastrarOrdemServico.cadastrar(input(), 1L)
        );

        assertEquals("exception.problema.notApproved", exception.getMessage());
        verify(ordemServicoRepository, never()).save(any());
    }

    private void prepararBusca() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(gestor));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(responsavel));
        when(problemaRepository.findById(3L)).thenReturn(Optional.of(problema));
    }

    private OrdemServicoCadastrarInputDTO input() {
        return new OrdemServicoCadastrarInputDTO(
                3L, 2L, StatusOrdemServico.NAO_INICIADA, CategoriaProblema.ELETRICA,
                LocalDate.of(2026, 10, 20), Prioridade.MEDIA);
    }
}
