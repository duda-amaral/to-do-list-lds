package com.labdessoft.todolist.mock;

import com.labdessoft.todolist.entity.Task;
import lombok.experimental.UtilityClass;

import java.util.UUID;

import static com.labdessoft.todolist.enums.Prioridade.ALTA;
import static com.labdessoft.todolist.enums.Prioridade.BAIXA;
import static com.labdessoft.todolist.enums.TaskTipo.LIVRE;
import static java.util.UUID.randomUUID;

@UtilityClass
public class TaskMock {
    public static Task createTask() {
        return Task.builder()
                .id(40L)
                .description("tarefa de fpaa")
                .priority(BAIXA)
                .completed(false)
                .type(LIVRE)
                .build();
    }

    public static Task updateTask() {
        return Task.builder()
                .id(40L)
                .description("tarefa de fpaa atualizada")
                .priority(ALTA)
                .completed(false)
                .type(LIVRE)
                .build();
    }
}
