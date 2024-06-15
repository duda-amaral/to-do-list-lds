package com.labdessoft.todolist.unit.service;

import com.labdessoft.todolist.entity.TaskPrazo;
import com.labdessoft.todolist.repository.TaskPrazoRepository;
import com.labdessoft.todolist.service.TaskPrazoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.junit.jupiter.api.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.*;

import static com.labdessoft.todolist.mock.TaskMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskPrazoServiceTest {

    @Mock
    TaskPrazoRepository taskPrazoRepository;

    @InjectMocks
    private TaskPrazoService taskPrazoService;

    private TaskPrazo createTaskPrazo;

    private TaskPrazo updateTaskPrazo;

    @BeforeEach
    public void setup() {
        createTaskPrazo = createTaskPrazo();
        updateTaskPrazo = updateTaskPrazo();
    }

    @Test
    public void createTaskPrazo_Success() {
        when(taskPrazoRepository.save(any(TaskPrazo.class))).thenAnswer(invocation -> invocation.getArgument(0));
        assertDoesNotThrow(() -> taskPrazoService.create(createTaskPrazo));
    }

    @Test
    public void updateTaskPrazoNotFound_RuntimeException() {
        when(taskPrazoRepository.findById(createTaskPrazo.getId())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> taskPrazoService.update(createTaskPrazo));
    }

    @Test
    public void updateTaskPrazo_Success() {
        when(taskPrazoRepository.findById(createTaskPrazo.getId())).thenReturn(Optional.of(createTaskPrazo));
        when(taskPrazoRepository.save(any(TaskPrazo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskPrazo updatedTaskPrazo = taskPrazoService.update(updateTaskPrazo);

        assertEquals(updatedTaskPrazo.getDescription(), updateTaskPrazo.getDescription());
        assertEquals(updatedTaskPrazo.getDueDays(), updateTaskPrazo.getDueDays());
    }

    @Test
    public void deleteTaskPrazo_Success() {
        when(taskPrazoRepository.findById(createTaskPrazo.getId())).thenReturn(Optional.of(createTaskPrazo));
        doNothing().when(taskPrazoRepository).delete(createTaskPrazo);
        assertDoesNotThrow(() -> taskPrazoService.delete(createTaskPrazo.getId()));
    }

    @Test
    public void findById_Success() {
        when(taskPrazoRepository.findById(createTaskPrazo.getId())).thenReturn(Optional.of(createTaskPrazo));
        TaskPrazo foundTaskPrazo = taskPrazoService.findById(createTaskPrazo.getId());
        assertNotNull(foundTaskPrazo);
        assertEquals(foundTaskPrazo.getId(), createTaskPrazo.getId());
    }

    @Test
    public void findById_NotFound() {
        when(taskPrazoRepository.findById(createTaskPrazo.getId())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> taskPrazoService.findById(createTaskPrazo.getId()));
    }

    @Test
    public void listAll_Success() {
        when(taskPrazoRepository.findAll()).thenReturn(List.of(createTaskPrazo));
        List<TaskPrazo> taskPrazoList = taskPrazoService.listAll();
        assertNotNull(taskPrazoList);
        assertFalse(taskPrazoList.isEmpty());
        assertEquals(1, taskPrazoList.size());
    }

    @Test
    public void listAll_Empty() {
        when(taskPrazoRepository.findAll()).thenReturn(Collections.emptyList());
        List<TaskPrazo> taskPrazoList = taskPrazoService.listAll();
        assertNotNull(taskPrazoList);
        assertTrue(taskPrazoList.isEmpty());
    }

    @Test
    public void updateStatus_Success() {
        createTaskPrazo.setCompleted(true);
        when(taskPrazoRepository.findById(createTaskPrazo.getId())).thenReturn(Optional.of(createTaskPrazo));
        when(taskPrazoRepository.save(any(TaskPrazo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskPrazo updatedTaskPrazo = taskPrazoService.updateStatus(createTaskPrazo);

        assertEquals("Concluída", updatedTaskPrazo.getStatus());
    }

    @Test
    public void getStatus_Completed() {
        createTaskPrazo.setCompleted(true);
        String status = taskPrazoService.getStatus(createTaskPrazo);
        assertEquals("Concluída", status);
    }

    @Test
    public void getStatus_Pending() {
        createTaskPrazo.setCompleted(false);
        createTaskPrazo.setDueDays(2);
        createTaskPrazo.setCreationDate(LocalDate.now().minusDays(1));
        String status = taskPrazoService.getStatus(createTaskPrazo);
        assertEquals("Prevista: 2 dias", status);
    }
}
