package com.example.segundoapiappfixa.auth.usecase;

import com.example.segundoapiappfixa.domain.model.RecuperarSenhaCodigo;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.RecuperarSenhaCodigoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.domain.service.auth.EmailRecuperacaoSender;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class EsqueciSenhaTest {
    @Test
    void solicitarSubstituiCodigoEEnviaEmail() {
        var usuarios = mock(UsuarioRepository.class);
        var codigos = mock(RecuperarSenhaCodigoRepository.class);
        var email = mock(EmailRecuperacaoSender.class);
        var usuario = new Usuario(); usuario.setId(1L); usuario.setNomeCompleto("Ana Silva");
        usuario.setEmail("ana@exemplo.com"); usuario.setEstaAtivo(true);
        when(usuarios.findByEmail("ana@exemplo.com")).thenReturn(Optional.of(usuario));

        var resultado = new EsqueciSenha(usuarios, codigos, email).solicitar("ana@exemplo.com");

        ArgumentCaptor<RecuperarSenhaCodigo> codigo = ArgumentCaptor.forClass(RecuperarSenhaCodigo.class);
        verify(codigos).deleteAll(1L);
        verify(codigos).save(codigo.capture());
        verify(email).enviarCodigo("ana@exemplo.com", "Ana", String.valueOf(codigo.getValue().getCodigo()), "3 minutos");
        assertEquals("Ana", resultado.nome());
        assertTrue(codigo.getValue().getCodigo() >= 100000 && codigo.getValue().getCodigo() <= 999999);
    }
}
