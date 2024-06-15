package com.labdessoft.todolist.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labdessoft.todolist.controller.TaskDataController;
import com.labdessoft.todolist.entity.TaskData;
import com.labdessoft.todolist.mock.TaskMock;
import com.labdessoft.todolist.service.TaskDataService;
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

@WebMvcTest(TaskDataController.class)
public class TaskDataControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskDataService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private TaskData taskData;

    @BeforeEach
    public void setUp() {
        taskData = TaskMock.createTaskData();
    }

    @Test
    public void givenTaskDataList_whenListAll_thenStatus200AndListReturned() throws Exception {
        List<TaskData> taskDataList = Arrays.asList(taskData);
        when(taskService.listAll()).thenReturn(taskDataList);

        mockMvc.perform(get("/taskdata"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(50L))
                .andExpect(jsonPath("$[0].description").value("tarefa de ihc"));
    }

    @Test
    public void givenTaskData_whenCreate_thenStatus201AndLocationHeader() throws Exception {
        when(taskService.create(any(TaskData.class))).thenReturn(taskData);

        mockMvc.perform(post("/taskdata")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskData)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    public void givenUpdatedTaskData_whenUpdate_thenStatus204() throws Exception {
        TaskData updatedTaskData = TaskMock.updateTaskData();
        when(taskService.update(any(TaskData.class))).thenReturn(updatedTaskData);

        mockMvc.perform(put("/taskdata/50")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTaskData)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenCompletedTaskData_whenUpdateStatus_thenStatus204() throws Exception {
        TaskData updatedTaskDataStatus = TaskMock.updateTaskData();
        updatedTaskDataStatus.setCompleted(true);
        when(taskService.updateStatus(any(TaskData.class))).thenReturn(updatedTaskDataStatus);

        mockMvc.perform(put("/taskdata/50/completed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTaskDataStatus)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenTaskDataId_whenDelete_thenStatus204() throws Exception {
        mockMvc.perform(delete("/taskdata/50"))
                .andExpect(status().isNoContent());
    }
}