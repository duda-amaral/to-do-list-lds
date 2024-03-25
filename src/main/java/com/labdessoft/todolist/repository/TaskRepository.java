package com.labdessoft.todolist.repository;

import com.labdessoft.todolist.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
