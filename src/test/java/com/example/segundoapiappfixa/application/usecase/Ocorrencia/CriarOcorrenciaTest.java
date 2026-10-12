package com.example.segundoapiappfixa.application.usecase.Ocorrencia;

import com.example.segundoapiappfixa.adapters.dto.input.Ocorrencia.OcorrenciaCadastrarInputDTO;
import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.Prioridade;
import com.example.segundoapiappfixa.domain.model.CategoriaEquipamento;
import com.example.segundoapiappfixa.domain.model.Endereco;
import com.example.segundoapiappfixa.domain.model.LocalEndereco;
import com.example.segundoapiappfixa.domain.repository.CategoriaEquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.FotoRepository;
import com.example.segundoapiappfixa.domain.repository.LocalEnderecoRepository;
import com.example.segundoapiappfixa.domain.repository.OcorrenciaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CriarOcorrenciaTest {
    @Mock private OcorrenciaRepository ocorrenciaRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private LocalEnderecoRepository localEnderecoRepository;
    @Mock private CategoriaEquipamentoRepository categoriaEquipamentoRepository;
    @Mock private FotoRepository fotoRepository;
    @InjectMocks private CriarOcorrencia criarOcorrencia;

    @Test
    void deveExigirPayloadNoCadastro() {
        RegraProblemaException exception = assertThrows(RegraProblemaException.class,
                () -> criarOcorrencia.cadastrar(null, 1L));

        assertEquals("exception.ocorrencia.required", exception.getMessage());
    }
}
