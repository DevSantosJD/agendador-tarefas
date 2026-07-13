package com.javanauta.agendadortarefas.buisness;

import com.javanauta.agendadortarefas.buisness.dto.TarefasDTO;
import com.javanauta.agendadortarefas.buisness.mapper.TarefaUpdateConverter;
import com.javanauta.agendadortarefas.buisness.mapper.TarefasConverter;
import com.javanauta.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.javanauta.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.javanauta.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.javanauta.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefaConverter;
    private final JwtUtil jwtUtil;
    private final TarefaUpdateConverter tarefaUpdateConverter;

    public TarefasDTO salvarTarefa(String token, TarefasDTO dto) {
        String email = jwtUtil.extractEmailToken(token.substring(7));


        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefaConverter.paraTarefaEntity(dto);

        return tarefaConverter.paraTerafaDTO(
                tarefasRepository.save(entity));
    }

    public List<TarefasDTO> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {

        return tarefaConverter.paraListTarefasDTO(
                tarefasRepository.findByDataEventoBetween(dataInicial, dataFinal));

    }

    //Retorna lista de tarefas por e-mail
    public List<TarefasDTO> buscaTarefasPorEmail(String token) {
        String email = jwtUtil.extractEmailToken(token.substring(7));

        List<TarefasEntity> listaTarefas = tarefasRepository.findByEmailUsuario(email);

        return tarefaConverter.paraListTarefasDTO(listaTarefas);
    }

    public void deleteTarefaPorId(String id) {
        try {
            tarefasRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao deletar tarefa por id, id inexistente",
                    e.getCause());
        }

    }

    public TarefasDTO alterarStatus(StatusNotificacaoEnum status, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada"));
            entity.setStatusNotificacaoEnum(status);
            return tarefaConverter.paraTerafaDTO(entity);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar tarefa" + e.getCause());
        }
    }

    public TarefasDTO updateDeTarefas(TarefasDTO dto, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada"));
            tarefaUpdateConverter.updateTarefas(dto, entity);
            return tarefaConverter.paraTerafaDTO(tarefasRepository.save(entity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar tarefa" + e.getCause());
        }

    }
}