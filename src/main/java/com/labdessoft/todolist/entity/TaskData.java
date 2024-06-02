package com.labdessoft.todolist.entity;

import com.labdessoft.todolist.enums.Prioridade;
import com.labdessoft.todolist.enums.TaskTipo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Schema(description = "Todos os detalhes de uma tarefa do tipo data")
@Table(name = "TASK_DATA")
public class TaskData extends Task{

    @Schema(description = "Data prevista de conclusão")
    private LocalDate dueDate;

    @Builder(builderMethodName = "taskDataBuilder")
    public TaskData(Long id, @Size(min = 10, message = "Descrição da tarefa deve possuir pelo menos 10 caracteres") String description,
                    Boolean completed,
                    Prioridade priority,
                    TaskTipo type,
                    String status,
                    LocalDate dueDate) {
        super(id, description, completed, priority, type, status);
        this.dueDate = dueDate;
    }

    public TaskData() {

    }
}
