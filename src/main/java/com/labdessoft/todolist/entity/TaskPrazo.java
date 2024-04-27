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
@Table(name = "TASK_PRAZO")
@Schema(description = "Todos os detalhes de uma tarefa de tipo com prazo")
public class TaskPrazo extends Task {
    @Schema(description = "Prazo previsto de conclusão em dias")
    private Integer dueDays;

    private LocalDate creationDate;
}
