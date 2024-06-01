package com.labdessoft.todolist.unit.service;

import com.labdessoft.todolist.entity.Task;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.labdessoft.todolist.mock.TaskMock.createTask;
import static com.labdessoft.todolist.mock.TaskMock.updateTask;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.*;

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

    @Test
    public void deleteTask_Success() {
        when(tasksRepository.findById(createTask.getId())).thenReturn(Optional.of(createTask));
        assertDoesNotThrow(() -> taskService.delete(createTask.getId()));
    }

    @Test
    public void findById_Success() {
        when(tasksRepository.findById(createTask.getId())).thenReturn(Optional.of(createTask));
        Task foundTask = taskService.findById(createTask.getId());
        assertNotNull(foundTask);
        assertEquals(foundTask.getId(), createTask.getId());
    }

    @Test
    public void findById_NotFound() {
        when(tasksRepository.findById(createTask.getId())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> taskService.findById(createTask.getId()));
    }

    @Test
    public void listAll_Success() {
        when(tasksRepository.findAll()).thenReturn(List.of(createTask));
        List<Task> tasks = taskService.listAll();
        assertNotNull(tasks);
        assertFalse(tasks.isEmpty());
        assertEquals(1, tasks.size());
    }

    @Test
    public void listAll_Empty() {
        when(tasksRepository.findAll()).thenReturn(Collections.emptyList());
        List<Task> tasks = taskService.listAll();
        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void updateStatus_Success() {
        createTask.setCompleted(true);
        when(tasksRepository.findById(createTask.getId())).thenReturn(Optional.of(createTask));
        when(tasksRepository.save(Mockito.any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task updatedTask = taskService.updateStatus(createTask);

        assertEquals("Concluída", updatedTask.getStatus());
    }

    @Test
    public void getStatus_Completed() {
        createTask.setCompleted(true);
        String status = taskService.getStatus(createTask);
        assertEquals("Concluída", status);
    }

    @Test
    public void getStatus_Pending() {
        createTask.setCompleted(false);
        String status = taskService.getStatus(createTask);
        assertEquals("Pendente", status);
    }
}
