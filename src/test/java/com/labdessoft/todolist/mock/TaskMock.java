package com.labdessoft.todolist.mock;

import com.labdessoft.todolist.entity.Task;
import lombok.experimental.UtilityClass;

import java.util.UUID;

import static com.labdessoft.todolist.enums.Prioridade.BAIXA;
import static com.labdessoft.todolist.enums.TaskTipo.LIVRE;
import static java.util.UUID.randomUUID;

@UtilityClass
public class TaskMock {
    public static Task createTasks() {
        return Task.builder()
                .id(1l)
                .description("tarefa de fpaa")
                .priority(BAIXA)
                .completed(false)
                .type(LIVRE)
                .build();
    }
}
