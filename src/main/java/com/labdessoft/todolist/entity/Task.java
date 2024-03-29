package com.labdessoft.todolist.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Override
    public String toString() {
        return "Task [" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ']';
    }
}
