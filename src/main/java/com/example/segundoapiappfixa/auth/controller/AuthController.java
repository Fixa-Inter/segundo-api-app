package com.example.segundoapiappfixa.auth.controller;

import com.example.segundoapiappfixa.auth.controller.contract.AuthControllerContract;
import com.example.segundoapiappfixa.auth.dto.*;
import com.example.segundoapiappfixa.auth.usecase.*;
import com.example.segundoapiappfixa.domain.model.auth.CadastroDados;
import com.example.segundoapiappfixa.domain.model.auth.LoginResultado;
import com.example.segundoapiappfixa.domain.model.auth.LoginGoogleResultado;
import com.example.segundoapiappfixa.domain.model.auth.RecuperacaoSenhaResultado;
import com.example.segundoapiappfixa.domain.model.auth.TokensAutenticacao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerContract {

    private final Cadastrar cadastrar;
    private final Login login;
    private final RenovarToken renovarToken;
    private final Logout logout;
    private final EsqueciSenha esqueciSenha;
    private final RedefinirSenha redefinirSenha;
    private final ListarOpcoesCadastro listarOpcoesCadastro;

    @Override
    @PostMapping("/cadastro")
    public ResponseEntity<LoginResponseDTO> cadastrar(
            @Valid @RequestBody CadastroUsuarioRequestDTO requestDTO
    ) {
        LoginResultado resultado = cadastrar.cadastrar(new CadastroDados(requestDTO.nomeCompleto(), requestDTO.email(),
                requestDTO.senha(), requestDTO.unidadeId(), requestDTO.cargo(), requestDTO.dataNascimento()));
        return ResponseEntity.status(201).body(toLoginResponse(resultado));
    }

    @Override
    @GetMapping("/instituicoes")
    public ResponseEntity<List<CadastroNomeOutputDTO>> listarInstituicoes() {
        return ResponseEntity.ok(listarOpcoesCadastro.listarInstituicoes().stream()
                .map(instituicao -> new CadastroNomeOutputDTO(instituicao.getId(), instituicao.getNome())).toList());
    }

    @Override
    @GetMapping("/instituicoes/{instituicaoId}/unidades")
    public ResponseEntity<List<CadastroNomeOutputDTO>> listarUnidades(
            @PathVariable Long instituicaoId
    ) {
        return ResponseEntity.ok(listarOpcoesCadastro.listarUnidades(instituicaoId).stream()
                .map(unidade -> new CadastroNomeOutputDTO(unidade.getId(), unidade.getNome())).toList());
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(toLoginResponse(login.login(requestDTO.email(), requestDTO.senha())));
    }

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDTO> refresh(
            @Valid @RequestBody RefreshTokenRequestDTO requestDTO
    ) {
        TokensAutenticacao tokens = renovarToken.renovar(requestDTO.refreshToken());
        return ResponseEntity.ok(new RefreshTokenResponseDTO(tokens.accessToken(), tokens.refreshToken()));
    }

    @Override
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestBody(required = false)
            RefreshTokenRequestDTO requestDTO
    ) {
        logout.executar(requestDTO == null ? null : requestDTO.refreshToken());
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("/login/google")
    public ResponseEntity<GoogleLoginResponseDTO> loginGoogle(
            @Valid @RequestBody GoogleLoginRequestDTO requestDTO
    ) {
        LoginGoogleResultado resultado = login.loginGoogle(requestDTO.idToken());
        TokensAutenticacao tokens = resultado.tokens();
        return ResponseEntity.ok(new GoogleLoginResponseDTO(resultado.status(), resultado.email(), resultado.nome(),
                tokens == null ? null : tokens.accessToken(), tokens == null ? null : tokens.refreshToken()));
    }

    @Override
    @PostMapping("/esqueceu-senha/{email}")
    public ResponseEntity<RedefinirSenhaOutputDTO> esqueceuSenha(
            @PathVariable String email
    ) {
        RecuperacaoSenhaResultado resultado = esqueciSenha.solicitar(email);
        return ResponseEntity.ok(new RedefinirSenhaOutputDTO(resultado.nome(), resultado.email(), resultado.mensagem()));
    }

    @Override
    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(
            @RequestBody RedefinirSenhaRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(redefinirSenha.redefinir(requestDTO.email(), requestDTO.codigo(), requestDTO.novaSenha()));
    }

    @Override
    @PostMapping("/validar-codigo")
    public ResponseEntity<Boolean> validarCodigo(
            @Valid
            @RequestBody
            ValidarCodigoRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(esqueciSenha.validarCodigo(requestDTO.email(), requestDTO.codigo()));
    }

    private LoginResponseDTO toLoginResponse(LoginResultado resultado) {
        return new LoginResponseDTO(resultado.email(), resultado.tokens().accessToken(), resultado.tokens().refreshToken());
    }
}
