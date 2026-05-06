package com.flavio.agendadortarefas.business;

import com.flavio.agendadortarefas.business.dto.TarefasDTO;
import com.flavio.agendadortarefas.business.mapper.TarefasConververter;
import com.flavio.agendadortarefas.business.mapper.TarefasUpdateConverter;
import com.flavio.agendadortarefas.infraestructure.entity.TarefasEntity;
import com.flavio.agendadortarefas.infraestructure.enums.StatusNotificacaoEnum;
import com.flavio.agendadortarefas.infraestructure.exceptions.ResourceNotfoundException;
import com.flavio.agendadortarefas.infraestructure.repository.TarefasRepository;
import com.flavio.agendadortarefas.infraestructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConververter tarefasConververter;
    private final JwtUtil jwtUtil;
    private final TarefasUpdateConverter tarefasUpdateConverter;

    public TarefasDTO gravarTarefa(String token, TarefasDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        dto.setDataCriacao(LocalDateTime.now());
        dto.setDataAlteracao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefasConververter.paraTarefasEntity(dto);

        return tarefasConververter.paraTarefasDTO(
                tarefasRepository.save(entity));
    }

    public List<TarefasDTO> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return tarefasConververter.paraListaTarefasDTO(
                tarefasRepository.findByDataEventoBetween(dataInicial, dataFinal));

    }

    public List<TarefasDTO> buscaTarefasPorEmail(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        List<TarefasEntity> listaTarefas = tarefasRepository.findByEmailUsuario(email);

        return tarefasConververter.paraListaTarefasDTO(listaTarefas);
    }

    public void deletarTarefaPorId(String id) {
        try {
            tarefasRepository.deleteById(id);
        } catch (ResourceNotfoundException e) {
            throw new ResourceNotfoundException("Erro ao deletar tarefa por id, id inexistente" + id,
                    e.getCause());
        }
    }

    public TarefasDTO alteraStatus(StatusNotificacaoEnum status, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id).
                    orElseThrow(() -> new ResourceNotfoundException("Tarefa não encontrada" + id));
            entity.setStatusNotificacaoEnum(status);
            entity.setDataAlteracao(LocalDateTime.now());

            return tarefasConververter.paraTarefasDTO(tarefasRepository.save(entity));
        } catch (ResourceNotfoundException e) {
            throw new ResourceNotfoundException("Erro ao alterar status da Tarefa" + e.getCause());
        }
    }

    public TarefasDTO updateTarefas(TarefasDTO dto, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id).
                    orElseThrow(() -> new ResourceNotfoundException("Tarefa não encontrada" + id));
            tarefasUpdateConverter.updateTarefas(dto, entity);
            entity.setDataAlteracao(LocalDateTime.now());
            return tarefasConververter.paraTarefasDTO(tarefasRepository.save(entity));

        } catch (ResourceNotfoundException e) {
            throw new ResourceNotfoundException("Erro ao alterar status da Tarefa" + e.getCause());
        }
    }
}