package com.example.segundoapiappfixa.infrastructure.database.repository;

import com.example.segundoapiappfixa.infrastructure.database.entity.TarefaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaTarefaRepository extends JpaRepository<TarefaEntity, Long> {

    List<TarefaEntity> findAllByOrdemServico_Id(Long id);


    Long countByOrdemServico_Id(Long ordemServicoId);

}
