package com.labdessoft.todolist.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.labdessoft.todolist.controller.TaskPrazoController;
import com.labdessoft.todolist.entity.TaskPrazo;
import com.labdessoft.todolist.enums.Prioridade;
import com.labdessoft.todolist.enums.TaskTipo;
import com.labdessoft.todolist.service.TaskPrazoService;
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
@WebMvcTest(TaskPrazoController.class)
public class TaskPrazoControllerTest {

    @MockBean
    private TaskPrazoService taskService;

    private MockMvc mockMvc;

    @InjectMocks
    private TaskPrazoController taskController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testListAll() throws Exception {
        TaskPrazo task1 = new TaskPrazo(1L, "Test Task 1", false, Prioridade.ALTA, TaskTipo.LIVRE, "Pendente", 5, LocalDate.now());
        TaskPrazo task2 = new TaskPrazo(2L, "Test Task 2", false, Prioridade.MÉDIA, TaskTipo.LIVRE, "Pendente", 10, LocalDate.now());
        List<TaskPrazo> tasks = Arrays.asList(task1, task2);

        when(taskService.listAll()).thenReturn(tasks);

        mockMvc.perform(get("/taskprazo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    public void testCreateTask() throws Exception {
        TaskPrazo task = new TaskPrazo(1L, "Test Task", false, Prioridade.ALTA, TaskTipo.LIVRE, "Pendente", 5, LocalDate.now());
        when(taskService.create(any(TaskPrazo.class))).thenReturn(task);

        mockMvc.perform(post("/taskprazo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    public void testUpdateTask() throws Exception {
        TaskPrazo task = new TaskPrazo(1L, "Updated Task", false, Prioridade.ALTA, TaskTipo.LIVRE, "Pendente", 5, LocalDate.now());
        when(taskService.update(any(TaskPrazo.class))).thenReturn(task);

        mockMvc.perform(put("/taskprazo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateTaskStatus() throws Exception {
        TaskPrazo task = new TaskPrazo(1L, "Test Task", true, Prioridade.ALTA, TaskTipo.LIVRE, "Concluída", 5, LocalDate.now());
        when(taskService.updateStatus(any(TaskPrazo.class))).thenReturn(task);

        mockMvc.perform(put("/taskprazo/1/completed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteTask() throws Exception {
        doNothing().when(taskService).delete(1L);

        mockMvc.perform(delete("/taskprazo/1"))
                .andExpect(status().isNoContent());
    }
}
