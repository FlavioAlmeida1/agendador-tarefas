package com.flavio.agendadortarefas.business;

import com.flavio.agendadortarefas.business.dto.TarefasDTO;
import com.flavio.agendadortarefas.business.mapper.TarefasConververter;
import com.flavio.agendadortarefas.infraestructure.entity.TarefasEntity;
import com.flavio.agendadortarefas.infraestructure.enums.StatusNotificacaoEnum;
import com.flavio.agendadortarefas.infraestructure.repository.TarefasRepository;
import com.flavio.agendadortarefas.infraestructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConververter tarefasConververter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefa(String token, TarefasDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefasConververter.paraTarefasEntity(dto);

        return tarefasConververter.paraTarefasDTO(
                tarefasRepository.save(entity));
    }
}
