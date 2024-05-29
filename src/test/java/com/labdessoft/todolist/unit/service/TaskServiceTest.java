package com.labdessoft.todolist.unit.service;

import com.labdessoft.todolist.entity.Task;
import com.labdessoft.todolist.mock.TaskMock;
import com.labdessoft.todolist.repository.TaskRepository;
import com.labdessoft.todolist.service.TaskService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.labdessoft.todolist.mock.TaskMock.createTask;
import static com.labdessoft.todolist.mock.TaskMock.updateTask;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskServiceTest {
    @Mock
    TaskRepository tasksRepository;

    @InjectMocks
    private TaskService taskService;

    private Task createTask;

    private Task updateTask;

    @BeforeEach
    public void setup() {
        createTask = createTask();
        updateTask = updateTask();
    }

    @Test
    public void createTask_Success() {

        when(tasksRepository.save(Mockito.any(Task.class))).thenAnswer(invocation ->
                invocation.getArgument(0));

        assertDoesNotThrow(() -> taskService.create(createTask));
    }

    @Test
    public void updateTaskNotFound_RuntimeException() {
        when(tasksRepository.findById(createTask.getId())).thenReturn(Optional.empty());

            assertThrows(RuntimeException.class, () ->
                    taskService.update(createTask));
    }

    @Test
    public void updateTask_Success() {
        when(tasksRepository.findById(createTask.getId())).thenReturn(Optional.of(createTask));

        when(tasksRepository.save(Mockito.any(Task.class))).thenAnswer(invocation ->
                invocation.getArgument(0));

        Task updatedTask = taskService.update(updateTask);

        assertEquals(updatedTask.getDescription(), createTask.getDescription());
        assertEquals(updatedTask.getPriority(), createTask.getPriority());
    }
}
