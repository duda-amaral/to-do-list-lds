package com.labdessoft.todolist.unit.repository;

import com.labdessoft.todolist.entity.Task;
import com.labdessoft.todolist.enums.Prioridade;
import com.labdessoft.todolist.enums.TaskTipo;
import com.labdessoft.todolist.repository.TaskRepository;
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
public class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Task task;

    @BeforeEach
    public void setup() {

        task = Task.builder()
                .description("This is a test task")
                .completed(false)
                .priority(Prioridade.ALTA)
                .type(TaskTipo.LIVRE)
                .status("Pendente")
                .build();

        entityManager.persist(task);
    }

    @Test
    public void whenFindById_thenReturnTask() {
        Optional<Task> foundTask = taskRepository.findById(task.getId());

        assertThat(foundTask).isPresent();
        assertThat(foundTask.get().getDescription()).isEqualTo(task.getDescription());
    }

    @Test
    public void whenFindAll_thenReturnTaskList() {
        List<Task> tasks = taskRepository.findAll();

        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getDescription()).isEqualTo(task.getDescription());
    }

    @Test
    public void whenSave_thenReturnSavedTask() {
        Task newTask = Task.builder()
                .description("New Task Description")
                .completed(false)
                .priority(Prioridade.ALTA)
                .type(TaskTipo.LIVRE)
                .status("Pendente")
                .build();

        Task savedTask = taskRepository.save(newTask);

        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isNotNull();
        assertThat(savedTask.getDescription()).isEqualTo(newTask.getDescription());
    }

    @Test
    public void whenDelete_thenRemoveTask() {
        taskRepository.delete(task);

        Optional<Task> foundTask = taskRepository.findById(task.getId());
        assertThat(foundTask).isNotPresent();
    }

    @Test
    public void whenUpdate_thenReturnUpdatedTask() {
        task.setDescription("Updated Task Description");
        Task updatedTask = taskRepository.save(task);

        assertThat(updatedTask.getDescription()).isEqualTo("Updated Task Description");
    }

}
