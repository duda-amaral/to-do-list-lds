package com.labdessoft.todolist.mock;

import com.labdessoft.todolist.entity.Task;
import com.labdessoft.todolist.entity.TaskData;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

import static com.labdessoft.todolist.enums.Prioridade.*;
import static com.labdessoft.todolist.enums.TaskTipo.DATA;
import static com.labdessoft.todolist.enums.TaskTipo.LIVRE;

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

    public static TaskData createTaskData() {
        return TaskData.taskDataBuilder()
                .id(50L)
                .description("tarefa de ihc")
                .priority(MÉDIA)
                .completed(false)
                .type(DATA)
                .dueDate(LocalDate.of(2024, 06, 04))
                .build();
    }

    public static TaskData updateTaskData() {
        return TaskData.taskDataBuilder()
                .id(50L)
                .description("tarefa de ihc atualizada")
                .priority(ALTA)
                .completed(false)
                .type(DATA)
                .dueDate(LocalDate.of(2024, 06, 06))
                .build();
    }
}
