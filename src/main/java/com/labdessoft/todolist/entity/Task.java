package com.labdessoft.todolist.entity;

import com.labdessoft.todolist.enums.Prioridade;
import com.labdessoft.todolist.enums.TaskTipo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
@Schema(description = "Todos os detalhes de uma tarefa")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(name = "Descrição da tarefa deve possuir pelo menos 10 caracteres")
    @Size(min = 10, message = "Descrição da tarefa deve possuir pelo menos 10 caracteres")
    private String description;

    private  Boolean completed;

    @Enumerated(EnumType.STRING)
    private TaskTipo type;

    private LocalDate dueDate; // Data prevista de conclusão

    private Integer dueDays; // Prazo previsto de conclusão em dias

    @Enumerated(EnumType.STRING)
    private Prioridade priority;

    @Override
    public String toString() {
        return "Task [" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ']';
    }
}
