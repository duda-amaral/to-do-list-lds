package com.labdessoft.todolist.repository;

import com.labdessoft.todolist.entity.TaskData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskDataRepository extends JpaRepository<TaskData, Long> {
}
