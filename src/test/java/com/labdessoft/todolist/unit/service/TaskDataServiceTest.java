package com.labdessoft.todolist.unit.service;

import com.labdessoft.todolist.entity.TaskData;
import com.labdessoft.todolist.repository.TaskDataRepository;
import com.labdessoft.todolist.service.TaskDataService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
public class TaskDataServiceTest {
    @Mock
    TaskDataRepository taskDataRepository;

    @InjectMocks
    private TaskDataService taskDataService;

    private TaskData createTaskData;

    private TaskData updateTaskData;

    @BeforeEach
    public void setup() {
        createTaskData = createTaskData();
        updateTaskData = updateTaskData();
    }

    @Test
    public void createTaskData_Success() {
        when(taskDataRepository.save(Mockito.any(TaskData.class))).thenAnswer(invocation -> invocation.getArgument(0));
        assertDoesNotThrow(() -> taskDataService.create(createTaskData));
    }

    @Test
    public void updateTaskDataNotFound_RuntimeException() {
        when(taskDataRepository.findById(createTaskData.getId())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> taskDataService.update(createTaskData));
    }

    @Test
    public void updateTaskData_Success() {
        when(taskDataRepository.findById(createTaskData.getId())).thenReturn(Optional.of(createTaskData));
        when(taskDataRepository.save(Mockito.any(TaskData.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskData updatedTaskData = taskDataService.update(updateTaskData);

        assertEquals(updatedTaskData.getDescription(), updateTaskData.getDescription());
        assertEquals(updatedTaskData.getDueDate(), updateTaskData.getDueDate());
    }

    @Test
    public void deleteTaskData_Success() {
        when(taskDataRepository.findById(createTaskData.getId())).thenReturn(Optional.of(createTaskData));
        doNothing().when(taskDataRepository).delete(createTaskData);
        assertDoesNotThrow(() -> taskDataService.delete(createTaskData.getId()));
    }

    @Test
    public void findById_Success() {
        when(taskDataRepository.findById(createTaskData.getId())).thenReturn(Optional.of(createTaskData));
        TaskData foundTaskData = taskDataService.findById(createTaskData.getId());
        assertNotNull(foundTaskData);
        assertEquals(foundTaskData.getId(), createTaskData.getId());
    }

    @Test
    public void findById_NotFound() {
        when(taskDataRepository.findById(createTaskData.getId())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> taskDataService.findById(createTaskData.getId()));
    }

    @Test
    public void listAll_Success() {
        when(taskDataRepository.findAll()).thenReturn(List.of(createTaskData));
        List<TaskData> taskDataList = taskDataService.listAll();
        assertNotNull(taskDataList);
        assertFalse(taskDataList.isEmpty());
        assertEquals(1, taskDataList.size());
    }

    @Test
    public void listAll_Empty() {
        when(taskDataRepository.findAll()).thenReturn(Collections.emptyList());
        List<TaskData> taskDataList = taskDataService.listAll();
        assertNotNull(taskDataList);
        assertTrue(taskDataList.isEmpty());
    }

    @Test
    public void updateStatus_Success() {
        createTaskData.setCompleted(true);
        when(taskDataRepository.findById(createTaskData.getId())).thenReturn(Optional.of(createTaskData));
        when(taskDataRepository.save(any(TaskData.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskData updatedTaskData = taskDataService.updateStatus(createTaskData);

        assertEquals("Concluída", updatedTaskData.getStatus());
    }

    @Test
    public void getStatus_Completed() {
        createTaskData.setCompleted(true);
        String status = taskDataService.getStatus(createTaskData);
        assertEquals("Concluída", status);
    }

    @Test
    public void getStatus_Pending() {
        createTaskData.setCompleted(false);
        createTaskData.setDueDate(LocalDate.now().plusDays(1));
        String status = taskDataService.getStatus(createTaskData);
        assertEquals("Prevista: " + createTaskData.getDueDate(), status);
    }
}
