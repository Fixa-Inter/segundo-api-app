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

    private final JpaOrdemServicoRepository jpaOrdemServicoRepository;
    private final OrdemServicoMapper ordemServicoMapper;

    @Override
    public List<OrdemServico> findAllByProblemaIds(List<Long> problemaIds) {
        if (problemaIds.isEmpty()) {
            return List.of();
        }

        return jpaOrdemServicoRepository.findAllByProblema_IdIn(problemaIds).stream()
                .map(ordemServicoMapper::toModel)
                .toList();
    }

    @Override
    public Optional<OrdemServico> findById(Long id) {
        return jpaOrdemServicoRepository.findById(id)
                .map(ordemServicoMapper::toModel);
    }

    @Override
    public void delete(Long id) {
        jpaOrdemServicoRepository.deleteById(id);
    }

    @Override
    public List<OrdemServico> findByUsuarioId(Long usuarioId) {
        return jpaOrdemServicoRepository
                .findByUsuario_Id(usuarioId)
                .stream()
                .map(ordemServicoMapper::toModel)
                .toList();


    }
}