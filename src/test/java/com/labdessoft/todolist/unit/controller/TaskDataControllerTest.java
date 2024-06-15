package com.labdessoft.todolist.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.labdessoft.todolist.controller.TaskDataController;
import com.labdessoft.todolist.entity.TaskData;
import com.labdessoft.todolist.enums.Prioridade;
import com.labdessoft.todolist.enums.TaskTipo;
import com.labdessoft.todolist.service.TaskDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TaskDataController.class)
public class TaskDataControllerTest {

    @MockBean
    private TaskDataService taskService;

    private MockMvc mockMvc;

    @InjectMocks
    private TaskDataController taskController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testListAll() throws Exception {
        TaskData task1 = new TaskData(1L, "Test Task 1", false, Prioridade.ALTA, TaskTipo.LIVRE, "Pendente", LocalDate.now());
        TaskData task2 = new TaskData(2L, "Test Task 2", false, Prioridade.MÉDIA, TaskTipo.LIVRE, "Pendente", LocalDate.now());
        List<TaskData> tasks = Arrays.asList(task1, task2);

        when(taskService.listAll()).thenReturn(tasks);

        mockMvc.perform(get("/taskdata"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    public void testCreateTask() throws Exception {
        TaskData task = new TaskData(1L, "Test Task", false, Prioridade.ALTA, TaskTipo.LIVRE, "Pendente", LocalDate.now());
        when(taskService.create(any(TaskData.class))).thenReturn(task);

        mockMvc.perform(post("/taskdata")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    public void testUpdateTask() throws Exception {
        TaskData task = new TaskData(1L, "Updated Task", false, Prioridade.ALTA, TaskTipo.LIVRE, "Pendente", LocalDate.now());
        when(taskService.update(any(TaskData.class))).thenReturn(task);

        mockMvc.perform(put("/taskdata/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateTaskStatus() throws Exception {
        TaskData task = new TaskData(1L, "Test Task", true, Prioridade.ALTA, TaskTipo.LIVRE, "Concluída", LocalDate.now());
        when(taskService.updateStatus(any(TaskData.class))).thenReturn(task);

        mockMvc.perform(put("/taskdata/1/completed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteTask() throws Exception {
        doNothing().when(taskService).delete(1L);

        mockMvc.perform(delete("/taskdata/1"))
                .andExpect(status().isNoContent());
    }
}
