package com.labdessoft.todolist.controller;


import com.labdessoft.todolist.entity.TaskPrazo;
import com.labdessoft.todolist.service.TaskPrazoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/taskprazo")
public class TaskPrazoController {
    @Autowired
    private TaskPrazoService taskService;
    @GetMapping
    @Operation(summary = "Lista todas as tarefas cadastradas")
    public ResponseEntity<List<TaskPrazo>> listAll() {
        try{
            List<TaskPrazo> taskList = this.taskService.listAll();

            if(taskList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(taskList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @Operation(summary = "Cadastra as tarefas")
    public ResponseEntity<TaskPrazo> create(@Valid @RequestBody TaskPrazo obj) {
        this.taskService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Atualiza a descrição de uma tarefa")
    @PutMapping("/{id}")
    public ResponseEntity<TaskPrazo> update(@Valid @RequestBody TaskPrazo obj, @PathVariable Long id) {
        obj.setId(id);
        this.taskService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza o status de uma tarefa")
    @PutMapping("/{id}/completed")
    public ResponseEntity<TaskPrazo> updateStatus(@Valid @RequestBody TaskPrazo obj, @PathVariable Long id) {
        obj.setId(id);
        obj.setCompleted(true);
        this.taskService.updateStatus(obj);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deleta uma tarefa pelo id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
