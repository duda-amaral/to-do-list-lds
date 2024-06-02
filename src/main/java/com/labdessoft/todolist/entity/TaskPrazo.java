package com.labdessoft.todolist.entity;


import com.labdessoft.todolist.enums.Prioridade;
import com.labdessoft.todolist.enums.TaskTipo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "TASK_PRAZO")
@Schema(description = "Todos os detalhes de uma tarefa de tipo com prazo")
public class TaskPrazo extends Task {
    @Schema(description = "Prazo previsto de conclusão em dias")
    private Integer dueDays;

    private LocalDate creationDate;

    @Builder(builderMethodName = "taskPrazoBuilder")
    public TaskPrazo(Long id, @Size(min = 10, message = "Descrição da tarefa deve possuir pelo menos 10 caracteres") String description, Boolean completed, Prioridade priority, TaskTipo type, String status, Integer dueDays, LocalDate creationDate) {
        super(id, description, completed, priority, type, status);
        this.dueDays = dueDays;
        this.creationDate = creationDate;
    }

    public TaskPrazo() {

    }
}
