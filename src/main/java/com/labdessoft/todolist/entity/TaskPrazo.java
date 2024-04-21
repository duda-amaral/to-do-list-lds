package com.labdessoft.todolist.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Todos os detalhes de uma tarefa de tipo com prazo")
public class TaskPrazo extends Task {
    private Integer dueDays; // Prazo previsto de conclusão em dias
}
