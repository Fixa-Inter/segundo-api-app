package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.OrdemServicoMapper;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.domain.repository.OrdemServicoRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.OrdemServicoEntity;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaOrdemServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrdemServicoRepositoryImpl implements OrdemServicoRepository {

    // Dependências
    private final JpaOrdemServicoRepository jpaOrdemServicoRepository;
    private final OrdemServicoMapper ordemServicoMapper;

    // Método de listar os registros persistidos no banco de dados
    @Override
    public List<OrdemServico> findAllByProblemaIds(List<Long> problemaIds) {
        if (problemaIds.isEmpty()) {
            return List.of();
        }

        return jpaOrdemServicoRepository.findAllByProblema_IdIn(problemaIds).stream()
                .map(ordemServicoMapper::toModel)
                .toList();
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public Optional<OrdemServico> findById(Long id) {
        return jpaOrdemServicoRepository.findById(id)
                .map(ordemServicoMapper::toModel);
    }

    // Método de deletar dados persistidos no banco de dados
    @Override
    public void delete(Long id) {
        jpaOrdemServicoRepository.deleteById(id);
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public List<OrdemServico> findByUsuarioId(Long usuarioId) {
        return jpaOrdemServicoRepository
                .findByUsuario_Id(usuarioId)
                .stream()
                .map(ordemServicoMapper::toModel)
                .toList();


    }
}
