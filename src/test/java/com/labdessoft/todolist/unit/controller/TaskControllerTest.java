package com.labdessoft.todolist.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labdessoft.todolist.controller.TaskController;
import com.labdessoft.todolist.entity.Task;
import com.labdessoft.todolist.enums.Prioridade;
import com.labdessoft.todolist.enums.TaskTipo;
import com.labdessoft.todolist.service.TaskService;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @MockBean
    private TaskService taskService;

    private MockMvc mockMvc;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void testGetTaskById() throws Exception {
        Task task = new Task(1L, "Test Task", false, Prioridade.ALTA, TaskTipo.LIVRE, "Pendente");
        when(taskService.findById(1L)).thenReturn(task);

        mockMvc.perform(get("/task/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.description").value("Test Task"));
    }

    @Test
    public void testListAll() throws Exception {
        Task task1 = new Task(1L, "Test Task 1", false, Prioridade.ALTA, TaskTipo.LIVRE, "Pendente");
        Task task2 = new Task(2L, "Test Task 2", false, Prioridade.MÉDIA, TaskTipo.LIVRE, "Pendente");
        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskService.listAll()).thenReturn(tasks);

        mockMvc.perform(get("/task"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    public void testCreateTask() throws Exception {
        Task task = new Task(1L, "Test Task", false, Prioridade.ALTA, TaskTipo.LIVRE, "Pendente");
        when(taskService.create(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    public void testUpdateTask() throws Exception {
        Task task = new Task(1L, "Updated Task", false, Prioridade.ALTA, TaskTipo.LIVRE, "Pendente");
        when(taskService.update(any(Task.class))).thenReturn(task);

        mockMvc.perform(put("/task/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(task)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateTaskStatus() throws Exception {
        Task task = new Task(1L, "Test Task", true, Prioridade.ALTA, TaskTipo.LIVRE, "Concluída");
        when(taskService.updateStatus(any(Task.class))).thenReturn(task);

        mockMvc.perform(put("/task/1/completed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(task)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteTask() throws Exception {
        doNothing().when(taskService).delete(1L);

        mockMvc.perform(delete("/task/1"))
                .andExpect(status().isNoContent());
    }
}
