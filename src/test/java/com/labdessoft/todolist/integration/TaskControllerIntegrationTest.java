package com.labdessoft.todolist.integration;

import com.labdessoft.todolist.ToDoListApplication;
import com.labdessoft.todolist.entity.Task;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static com.labdessoft.todolist.mock.TaskMock.createTask;
import static com.labdessoft.todolist.mock.TaskMock.updateTask;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@SpringBootTest(classes = {ToDoListApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private Task createTask;

    private Task updateTask;

    private static Long taskId = 40L;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        createTask = createTask();
        updateTask = updateTask();
    }

    @Order(3)
    @Test
    public void givenUrl_whenSuccessOnGetsResponseAndJsonHasRequiredKV_thenCorrect() {
        get("/api/task").then().statusCode(200);
    }

    @Order(2)
    @Test
    public void givenUrl_whenSuccessOnGetsResponseAndJsonHasOneTask_thenCorrect() {
        get("/api/task/{id}", taskId).then().statusCode(200)
                .assertThat().body("description", equalTo("tarefa de fpaa"));
    }

    @Order(1)
    @Test
    public void givenTask_whenPostRequestToCreateTask_thenTaskIsCreated() {

        given()
                .contentType("application/json")
                .body(createTask)
                .when()
                .post("/api/task")
                .then()
                .statusCode(201)
                .header("Location", notNullValue());
    }

    @Order(4)
    @Test
    public void givenTask_whenPutRequestToUpdateTask_thenTaskIsUpdated() {
        Long id = taskId;
        given()
                .contentType("application/json")
                .body(updateTask)
                .when()
                .put("/api/task/{id}", id)
                .then()
                .statusCode(204);
    }

    @Order(5)
    @Test
    public void givenTaskId_whenPutRequestToUpdateStatus_thenStatusIsUpdated() {
        String taskStatusUpdateJson = "{\"completed\":true}";

        given()
                .contentType("application/json")
                .body(taskStatusUpdateJson)
                .when()
                .put("api/task/{id}/completed", taskId)
                .then()
                .statusCode(204);
    }

    @Order(6)
    @Test
    public void givenTaskId_whenDeleteRequestToDeleteTask_thenTaskIsDeleted() {
        given()
                .when()
                .delete("/api/task/{id}", taskId)
                .then()
                .statusCode(204);
    }
}
