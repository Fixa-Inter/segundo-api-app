package com.example.segundoapiappfixa.infrastructure.database.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.FotoMapper;
import com.example.segundoapiappfixa.domain.model.Foto;
import com.example.segundoapiappfixa.domain.repository.FotoRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.FotoEntity;
import com.example.segundoapiappfixa.infrastructure.database.repository.JpaFotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FotoProblemaRepositoryImpl implements FotoRepository {

    // Dependências
    private final JpaFotoRepository repository;
    private final FotoMapper mapper;

    // Método de salvar no banco de dados
    @Override
    public List<Foto> findAllByProblemaId(Long problemaId) {
        return repository.
                findByProblema_Id(problemaId)
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    // Método de listar os registros persistidos no banco de dados
    @Override
    public Foto save(Foto foto) {
        FotoEntity fotoEntity = mapper.toEntity(foto);

        FotoEntity fotoPersistida = repository.save(fotoEntity);
        return mapper.toModel(fotoPersistida);
    }
}
