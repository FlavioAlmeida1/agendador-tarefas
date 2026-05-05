package com.flavio.agendadortarefas.business.mapper;

import com.flavio.agendadortarefas.business.dto.TarefasDTO;
import com.flavio.agendadortarefas.infraestructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefasConververter {

    TarefasEntity paraTarefasEntity(TarefasDTO dto);

    TarefasDTO paraTarefasDTO(TarefasEntity entity);
}
