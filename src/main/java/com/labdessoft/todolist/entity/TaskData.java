package com.labdessoft.todolist.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Todos os detalhes de uma tarefa do tipo data")
@Table(name = "TASK_DATA")
public class TaskData extends Task{

    @Schema(description = "Data prevista de conclusão")
    private LocalDate dueDate;
}
