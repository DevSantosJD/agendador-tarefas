package com.javanauta.agendadortarefas.buisness.mapper;

import com.javanauta.agendadortarefas.buisness.dto.TarefasDTO;
import com.javanauta.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TarefaUpdateConverter {

    /*
    O metodo abaixo, atualiza a tarefa com os dados vindo do dto.
    O que não vem do dto, é pego da entity, conservando os dados não atualizados
    e montando o objeto trefa.
     */
    void updateTarefas(TarefasDTO dto, @MappingTarget TarefasEntity entity);
}
