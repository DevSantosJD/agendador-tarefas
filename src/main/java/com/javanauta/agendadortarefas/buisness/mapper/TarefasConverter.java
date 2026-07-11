package com.javanauta.agendadortarefas.buisness.mapper;

import com.javanauta.agendadortarefas.buisness.dto.TarefasDTO;
import com.javanauta.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefasConverter {

    TarefasEntity paraTarefaEntity(TarefasDTO dto);

    TarefasDTO paraTerafaDTO(TarefasEntity entity);

    List<TarefasEntity> paraListaTarefasEntit (List<TarefasDTO> dtos);

   List<TarefasDTO> paraListTarefasDTO (List<TarefasEntity> tarefasEntityList);


}
