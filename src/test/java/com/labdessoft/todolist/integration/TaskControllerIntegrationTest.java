package com.labdessoft.todolist.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labdessoft.todolist.controller.TaskController;
import com.labdessoft.todolist.entity.Task;
import com.labdessoft.todolist.mock.TaskMock;
import com.labdessoft.todolist.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private Task task;

    @BeforeEach
    public void setUp() {
        task = TaskMock.createTask();
    }

    @Test
    public void givenTaskId_whenGetTaskById_thenStatus200AndTaskReturned() throws Exception {
        when(taskService.findById(40L)).thenReturn(task);

        mockMvc.perform(get("/task/40"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(40L))
                .andExpect(jsonPath("$.description").value("tarefa de fpaa"));
    }

    @Test
    public void whenListAllTasks_thenStatus200AndListReturned() throws Exception {
        List<Task> tasks = Arrays.asList(task);
        when(taskService.listAll()).thenReturn(tasks);

        mockMvc.perform(get("/task"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(40L))
                .andExpect(jsonPath("$[0].description").value("tarefa de fpaa"));
    }

    @Test
    public void givenTask_whenPostRequestToCreateTask_thenTaskIsCreated() throws Exception {
        when(taskService.create(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    public void givenTask_whenPutRequestToUpdateTask_thenTaskIsUpdated() throws Exception {
        Task updatedTask = TaskMock.updateTask();
        when(taskService.update(any(Task.class))).thenReturn(updatedTask);

        mockMvc.perform(put("/task/40")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTask)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenTaskId_whenPutRequestToUpdateStatus_thenStatusIsUpdated() throws Exception {
        Task updatedTaskStatus = TaskMock.updateTask();
        updatedTaskStatus.setCompleted(true);
        when(taskService.updateStatus(any(Task.class))).thenReturn(updatedTaskStatus);

        mockMvc.perform(put("/task/40/completed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTaskStatus)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenTasDataId_whenDeleteRequestToDeleteTask_thenTaskIsDeleted() throws Exception {
        mockMvc.perform(delete("/task/40"))
                .andExpect(status().isNoContent());
    }
}