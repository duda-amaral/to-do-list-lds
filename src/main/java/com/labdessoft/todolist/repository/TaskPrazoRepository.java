package com.labdessoft.todolist.repository;

import com.labdessoft.todolist.entity.TaskPrazo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskPrazoRepository extends JpaRepository<TaskPrazo, Long> {
}
