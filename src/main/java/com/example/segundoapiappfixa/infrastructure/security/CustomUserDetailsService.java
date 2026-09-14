package com.example.segundoapiappfixa.infrastructure.security;

import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.infrastructure.database.repository.impl.UsuarioRepositoryImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepositoryImpl repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = repository.findByEmail(email);

        return new CustomUserDetails(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getSenhaHash(),
                usuario.getTipoAcesso()
        );
    }
}
