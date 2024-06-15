package com.labdessoft.todolist.unit.repository;

import com.labdessoft.todolist.entity.Task;
import com.labdessoft.todolist.entity.TaskData;
import com.labdessoft.todolist.enums.Prioridade;
import com.labdessoft.todolist.enums.TaskTipo;
import com.labdessoft.todolist.repository.TaskDataRepository;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@RunWith(JUnitPlatform.class)
@ActiveProfiles("test")
@DataJpaTest
public class TaskDataRepositoryTest {
    @Autowired
    private TaskDataRepository taskRepository;

    @Autowired
    private TestEntityManager entityManager;

    private TaskData task;

    @BeforeEach
    public void setup() {

        task = TaskData.taskDataBuilder()
                .description("This is a test task")
                .completed(false)
                .priority(Prioridade.ALTA)
                .type(TaskTipo.DATA)
                .status("Pendente")
                .dueDate(LocalDate.of(2025, 06, 04))
                .build();

        entityManager.persist(task);
    }

    @Test
    public void whenFindById_thenReturnTaskData() {
        Optional<TaskData> foundTask = taskRepository.findById(task.getId());

        assertThat(foundTask).isPresent();
        assertThat(foundTask.get().getDescription()).isEqualTo(task.getDescription());
    }

    @Test
    public void whenFindAll_thenReturnTaskDataList() {
        List<TaskData> tasks = taskRepository.findAll();

        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getDescription()).isEqualTo(task.getDescription());
    }

    @Test
    public void whenSave_thenReturnSavedTaskData() {
        TaskData newTask = TaskData.taskDataBuilder()
                .description("New Task Description")
                .completed(false)
                .priority(Prioridade.ALTA)
                .type(TaskTipo.LIVRE)
                .status("Pendente")
                .dueDate(LocalDate.of(2025, 06, 04))
                .build();

        TaskData savedTask = taskRepository.save(newTask);

        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isNotNull();
        assertThat(savedTask.getDescription()).isEqualTo(newTask.getDescription());
    }

    @Test
    public void whenDelete_thenRemoveTaskData() {
        taskRepository.delete(task);

        Optional<TaskData> foundTask = taskRepository.findById(task.getId());
        assertThat(foundTask).isNotPresent();
    }

    @Test
    public void whenUpdate_thenReturnUpdatedTaskData() {
        task.setDescription("Updated Task Description");
        TaskData updatedTask = taskRepository.save(task);

        assertThat(updatedTask.getDescription()).isEqualTo("Updated Task Description");
    }
}
