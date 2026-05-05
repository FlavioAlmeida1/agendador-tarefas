package com.flavio.agendadortarefas.controller;

import com.flavio.agendadortarefas.business.TarefasService;
import com.flavio.agendadortarefas.business.dto.TarefasDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefasController {

    private final TarefasService tarefasService;

  @PostMapping
  public ResponseEntity gravarTarefas(@RequestBody TarefasDTO dto,
                                      @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(tarefasService.gravarTarefa(token , dto));
  }

}
