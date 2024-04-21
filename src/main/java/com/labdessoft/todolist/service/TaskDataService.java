package com.labdessoft.todolist.service;

import com.labdessoft.todolist.entity.TaskData;

import java.time.LocalDate;

public class TaskDataService {



    private void validateTask(TaskData task) {
        if (task.getDueDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("A data prevista de execução deve ser igual ou superior à data atual.");
        }
    }
}
