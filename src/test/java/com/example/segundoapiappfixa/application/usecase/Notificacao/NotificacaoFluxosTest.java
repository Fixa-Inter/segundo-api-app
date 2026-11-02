package com.example.segundoapiappfixa.application.usecase.Notificacao;

import com.example.segundoapiappfixa.application.usecase.Evento.CadastrarEvento;
import com.example.segundoapiappfixa.application.usecase.OrdemServico.AtualizarOrdemServico;
import com.example.segundoapiappfixa.application.usecase.OrdemServico.CadastrarOrdemServico;
import com.example.segundoapiappfixa.application.usecase.Problema.AtualizarStatus;
import com.example.segundoapiappfixa.application.usecase.Problema.CriarProblema;
import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import com.example.segundoapiappfixa.domain.enums.StatusOrdemServico;
import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.Prioridade;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.enums.TipoNotificacao;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.model.input.Evento.EventoCadastrarDados;
import com.example.segundoapiappfixa.domain.model.input.OrdemServico.OrdemServicoAtualizarDados;
import com.example.segundoapiappfixa.domain.model.input.OrdemServico.OrdemServicoCadastrarDados;
import com.example.segundoapiappfixa.domain.model.input.Problema.ProblemaAtualizarStatusDados;
import com.example.segundoapiappfixa.domain.repository.*;
import com.example.segundoapiappfixa.domain.service.NotificacaoSender;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotificacaoFluxosTest {
    @Test
    void solicitacaoNotificaGestoresDaUnidadeDepoisDeSalvar() {
        ProblemaRepository problemas = mock(ProblemaRepository.class);
        LocalUnidadeRepository locais = mock(LocalUnidadeRepository.class);
        CategoriaEquipamentoRepository categorias = mock(CategoriaEquipamentoRepository.class);
        UsuarioRepository usuarios = mock(UsuarioRepository.class);
        NotificacaoSender sender = mock(NotificacaoSender.class);
        Unidade unidade = new Unidade(); unidade.setId(10L);
        LocalUnidade local = new LocalUnidade(); local.setId(2L); local.setUnidade(unidade);
        CategoriaEquipamento categoria = new CategoriaEquipamento(); categoria.setId(3L);
        Usuario autor = new Usuario(); autor.setId(1L);
        Usuario gestor = new Usuario(); gestor.setId(4L);
        Problema problema = new Problema(); problema.setLocalUnidade(local);
        problema.setCategoriaEquipamento(categoria); problema.setTitulo("Luz");
        when(locais.findById(2L)).thenReturn(Optional.of(local));
        when(categorias.findById(3L)).thenReturn(Optional.of(categoria));
        when(usuarios.findById(1L)).thenReturn(Optional.of(autor));
        when(problemas.save(problema, List.of())).thenReturn(5L);
        when(problemas.findById(5L)).thenReturn(Optional.of(problema));
        when(usuarios.findGestoresAtivosByUnidadeId(10L)).thenReturn(List.of(gestor));

        new CriarProblema(problemas, locais, categorias, usuarios, sender).cadastrar(problema, List.of(), 1L);

        verify(sender).notificarAposPersistencia(4L, TipoNotificacao.SOLICITACAO.getId(), 5L,
                "Nova solicitação registrada: Luz.");
        verifyNoMoreInteractions(sender);
    }

    @Test
    void statusNotificaAutorDepoisDeSalvar() {
        ProblemaRepository repository = mock(ProblemaRepository.class);
        NotificacaoSender sender = mock(NotificacaoSender.class);
        Usuario autor = new Usuario(); autor.setId(7L);
        Problema problema = new Problema(); problema.setId(5L); problema.setUsuario(autor);
        problema.setTitulo("Luz"); problema.setStatus(StatusProblema.PENDENTE);
        when(repository.findById(5L)).thenReturn(Optional.of(problema));
        when(repository.update(any())).thenAnswer(call -> call.getArgument(0));

        new AtualizarStatus(repository, sender).atualizar(new ProblemaAtualizarStatusDados(5L, StatusProblema.APROVADO, null));

        var ordem = inOrder(repository, sender);
        ordem.verify(repository).update(problema);
        ordem.verify(sender).notificarAposPersistencia(7L, TipoNotificacao.SOLICITACAO.getId(), 5L,
                "O status da sua solicitação \"Luz\" foi atualizado para " + StatusProblema.APROVADO.getNome() + ".");
    }

    @Test
    void eventoNotificaTodosOsUsuariosAtivosDaUnidade() {
        EventoRepository eventos = mock(EventoRepository.class);
        UsuarioRepository usuarios = mock(UsuarioRepository.class);
        LocalUnidadeRepository locais = mock(LocalUnidadeRepository.class);
        NotificacaoSender sender = mock(NotificacaoSender.class);
        Unidade unidade = new Unidade(); unidade.setId(10L);
        LocalUnidade local = new LocalUnidade(); local.setId(2L); local.setUnidade(unidade);
        Usuario autor = new Usuario(); autor.setId(1L);
        when(usuarios.findById(1L)).thenReturn(Optional.of(autor));
        when(locais.findById(2L)).thenReturn(Optional.of(local));
        when(eventos.save(any())).thenAnswer(call -> { Evento e = call.getArgument(0); e.setId(8L); return e; });
        when(usuarios.findIdsAtivosByUnidadeId(10L)).thenReturn(List.of(1L, 4L));

        new CadastrarEvento(eventos, usuarios, locais, sender).cadastrar(
                new EventoCadastrarDados(1L, 2L, "Reunião", "Descrição", "Sala", null, null, null), 1L);

        verify(sender).notificarAposPersistencia(1L, TipoNotificacao.EVENTO.getId(), 8L, "Novo evento: Reunião.");
        verify(sender).notificarAposPersistencia(4L, TipoNotificacao.EVENTO.getId(), 8L, "Novo evento: Reunião.");
        verifyNoMoreInteractions(sender);
    }

    @Test
    void cadastroDeOsNotificaResponsavel() {
        OrdemServicoRepository ordens = mock(OrdemServicoRepository.class);
        ProblemaRepository problemas = mock(ProblemaRepository.class);
        UsuarioRepository usuarios = mock(UsuarioRepository.class);
        NotificacaoSender sender = mock(NotificacaoSender.class);
        Unidade unidade = new Unidade(); unidade.setId(10L);
        LocalUnidade local = new LocalUnidade(); local.setUnidade(unidade);
        Problema problema = new Problema(); problema.setId(5L); problema.setTitulo("Luz");
        problema.setStatus(StatusProblema.APROVADO); problema.setLocalUnidade(local);
        Usuario gestor = new Usuario(); gestor.setId(1L); gestor.setUnidade(unidade);
        Usuario tecnico = new Usuario(); tecnico.setId(2L); tecnico.setUnidade(unidade); tecnico.setTipoAcesso(TipoAcesso.TECNICO);
        when(usuarios.findById(1L)).thenReturn(Optional.of(gestor));
        when(usuarios.findById(2L)).thenReturn(Optional.of(tecnico));
        when(problemas.findById(5L)).thenReturn(Optional.of(problema));
        when(ordens.save(any())).thenAnswer(call -> { OrdemServico os = call.getArgument(0); os.setId(8L); return os; });

        new CadastrarOrdemServico(ordens, problemas, usuarios, sender).cadastrar(
                new OrdemServicoCadastrarDados(5L, 2L, StatusOrdemServico.NAO_INICIADA,
                        CategoriaProblema.ELETRICA, LocalDate.of(2026, 12, 1), Prioridade.ALTA), 1L);

        verify(sender).notificarAposPersistencia(2L, TipoNotificacao.ORDEM_SERVICO.getId(), 8L,
                "Uma nova OS foi atribuída a você: Luz.");
    }

    @Test
    void osNotificaSomenteQuandoResponsavelMuda() {
        OrdemServicoRepository ordens = mock(OrdemServicoRepository.class);
        UsuarioRepository usuarios = mock(UsuarioRepository.class);
        NotificacaoSender sender = mock(NotificacaoSender.class);
        Unidade unidade = new Unidade(); unidade.setId(10L);
        LocalUnidade local = new LocalUnidade(); local.setUnidade(unidade);
        Problema problema = new Problema(); problema.setTitulo("Luz"); problema.setLocalUnidade(local);
        Usuario gestor = new Usuario(); gestor.setId(1L); gestor.setUnidade(unidade);
        Usuario tecnico = new Usuario(); tecnico.setId(2L); tecnico.setUnidade(unidade); tecnico.setTipoAcesso(TipoAcesso.TECNICO);
        Usuario outro = new Usuario(); outro.setId(3L); outro.setUnidade(unidade); outro.setTipoAcesso(TipoAcesso.TECNICO);
        OrdemServico os = new OrdemServico(); os.setId(8L); os.setProblema(problema); os.setUsuario(tecnico);
        when(ordens.findById(8L)).thenReturn(Optional.of(os));
        when(usuarios.findById(1L)).thenReturn(Optional.of(gestor));
        when(usuarios.findById(2L)).thenReturn(Optional.of(tecnico));
        when(usuarios.findById(3L)).thenReturn(Optional.of(outro));
        when(ordens.save(any())).thenAnswer(call -> call.getArgument(0));
        AtualizarOrdemServico atualizar = new AtualizarOrdemServico(ordens, usuarios, sender);

        atualizar.atualizar(new OrdemServicoAtualizarDados(8L, 2L, null, null, null, null), 1L, true);
        verifyNoInteractions(sender);
        atualizar.atualizar(new OrdemServicoAtualizarDados(8L, 3L, null, null, null, null), 1L, true);
        verify(sender).notificarAposPersistencia(3L, TipoNotificacao.ORDEM_SERVICO.getId(), 8L,
                "Uma nova OS foi atribuída a você: Luz.");
    }
}
