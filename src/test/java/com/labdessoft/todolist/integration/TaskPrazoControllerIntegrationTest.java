package com.labdessoft.todolist.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labdessoft.todolist.controller.TaskPrazoController;
import com.labdessoft.todolist.entity.TaskPrazo;
import com.labdessoft.todolist.mock.TaskMock;
import com.labdessoft.todolist.service.TaskPrazoService;
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

@WebMvcTest(TaskPrazoController.class)
public class TaskPrazoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskPrazoService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private TaskPrazo taskPrazo;

    @BeforeEach
    public void setUp() {
        taskPrazo = TaskMock.createTaskPrazo();
    }

    @Test
    public void givenTaskPrazoList_whenListAll_thenStatus200AndListReturned() throws Exception {
        List<TaskPrazo> taskPrazoList = Arrays.asList(taskPrazo);
        when(taskService.listAll()).thenReturn(taskPrazoList);

        mockMvc.perform(get("/taskprazo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(60L))
                .andExpect(jsonPath("$[0].description").value("tarefa de lds"));
    }

    @Test
    public void givenTaskPrazo_whenCreate_thenStatus201AndLocationHeader() throws Exception {
        when(taskService.create(any(TaskPrazo.class))).thenReturn(taskPrazo);

        mockMvc.perform(post("/taskprazo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskPrazo)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    public void givenUpdatedTaskPrazo_whenUpdate_thenStatus204() throws Exception {
        TaskPrazo updatedTaskPrazo = TaskMock.updateTaskPrazo();
        when(taskService.update(any(TaskPrazo.class))).thenReturn(updatedTaskPrazo);

        mockMvc.perform(put("/taskprazo/60")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTaskPrazo)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenCompletedTaskPrazo_whenUpdateStatus_thenStatus204() throws Exception {
        TaskPrazo updatedTaskPrazoStatus = TaskMock.updateTaskPrazo();
        updatedTaskPrazoStatus.setCompleted(true);
        when(taskService.updateStatus(any(TaskPrazo.class))).thenReturn(updatedTaskPrazoStatus);

        mockMvc.perform(put("/taskprazo/60/completed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTaskPrazoStatus)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenTaskPrazoId_whenDelete_thenStatus204() throws Exception {
        mockMvc.perform(delete("/taskprazo/60"))
                .andExpect(status().isNoContent());
    }
}