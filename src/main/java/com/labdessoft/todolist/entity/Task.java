package com.labdessoft.todolist.entity;

import com.labdessoft.todolist.enums.Prioridade;
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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Schema(description = "Todos os detalhes de uma tarefa livre")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Long id;

    @Schema(name = "Descrição da tarefa deve possuir pelo menos 10 caracteres")
    @Size(min = 10, message = "Descrição da tarefa deve possuir pelo menos 10 caracteres")
    protected String description;

    protected Boolean completed;

    @Enumerated(EnumType.STRING)
    protected Prioridade priority;

    @Override
    public String toString() {
        return "Task [" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ']';
    }
}
