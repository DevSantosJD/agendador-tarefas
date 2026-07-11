package com.javanauta.agendadortarefas.buisness;

import com.javanauta.agendadortarefas.buisness.dto.TarefasDTO;
import com.javanauta.agendadortarefas.buisness.mapper.TarefasConverter;
import com.javanauta.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.javanauta.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.javanauta.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.javanauta.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefaConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO salvarTarefa(String token, TarefasDTO dto){
        String email = jwtUtil.extractEmailToken(token.substring(7));


        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefaConverter.paraTarefaEntity(dto);

        return tarefaConverter.paraTerafaDTO(
                tarefasRepository.save(entity));
    }

    public List<TarefasDTO> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal){

        return tarefaConverter.paraListTarefasDTO(
                tarefasRepository.findByDataEventoBetween(dataInicial, dataFinal));

    }

    //Retorna lista de tarefas por email
    public List<TarefasDTO> buscaTarefasPorEmail(String token){
        String email = jwtUtil.extractEmailToken(token.substring(7));

        List<TarefasEntity> listaTarefas = tarefasRepository.findByEmailUsuario(email);

        return tarefaConverter.paraListTarefasDTO(listaTarefas);
    }
}
