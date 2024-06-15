package com.labdessoft.todolist.unit.repository;

import com.labdessoft.todolist.entity.TaskPrazo;
import com.labdessoft.todolist.enums.Prioridade;
import com.labdessoft.todolist.enums.TaskTipo;
import com.labdessoft.todolist.repository.TaskPrazoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@RunWith(JUnitPlatform.class)
@ActiveProfiles("test")
@DataJpaTest
public class TaskPrazoRepositoryTest {
    @Autowired
    private TaskPrazoRepository taskRepository;

    @Autowired
    private TestEntityManager entityManager;

    private TaskPrazo task;

    @BeforeEach
    public void setup() {

        task = TaskPrazo.taskPrazoBuilder()
                .description("This is a test task")
                .completed(false)
                .priority(Prioridade.ALTA)
                .type(TaskTipo.PRAZO)
                .status("Pendente")
                .dueDays(10)
                .build();

        entityManager.persist(task);
    }

    @Test
    public void whenFindById_thenReturnTaskPrazo() {
        Optional<TaskPrazo> foundTask = taskRepository.findById(task.getId());

        assertThat(foundTask).isPresent();
        assertThat(foundTask.get().getDescription()).isEqualTo(task.getDescription());
    }

    @Test
    public void whenFindAll_thenReturnTaskPrazoList() {
        List<TaskPrazo> tasks = taskRepository.findAll();

        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getDescription()).isEqualTo(task.getDescription());
    }

    @Test
    public void whenSave_thenReturnSavedTaskPrazo() {
        TaskPrazo newTask = TaskPrazo.taskPrazoBuilder()
                .description("New Task Description")
                .completed(false)
                .priority(Prioridade.ALTA)
                .type(TaskTipo.LIVRE)
                .status("Pendente")
                .dueDays(10)
                .build();

        TaskPrazo savedTask = taskRepository.save(newTask);

        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isNotNull();
        assertThat(savedTask.getDescription()).isEqualTo(newTask.getDescription());
    }

    @Test
    public void whenDelete_thenRemoveTaskPrazo() {
        taskRepository.delete(task);

        Optional<TaskPrazo> foundTask = taskRepository.findById(task.getId());
        assertThat(foundTask).isNotPresent();
    }

    @Test
    public void whenUpdate_thenReturnUpdatedTaskPrazo() {
        task.setDescription("Updated Task Description");
        TaskPrazo updatedTask = taskRepository.save(task);

        assertThat(updatedTask.getDescription()).isEqualTo("Updated Task Description");
    }

}
