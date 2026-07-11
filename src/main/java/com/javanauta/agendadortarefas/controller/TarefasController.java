package com.javanauta.agendadortarefas.controller;

import com.javanauta.agendadortarefas.buisness.TarefasService;
import com.javanauta.agendadortarefas.buisness.dto.TarefasDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefasController {

    private final TarefasService tarefasService;

    @PostMapping
    public ResponseEntity<TarefasDTO> salvarTarefa(@RequestBody TarefasDTO dto,
                                                   @RequestHeader("Authorization") String token){
        System.out.println("TOKEN NO CONTROLLER: " + token);
        return ResponseEntity.ok(tarefasService.salvarTarefa(token, dto));
    }

}
