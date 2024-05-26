package com.labdessoft.todolist.integration;

import com.labdessoft.todolist.ToDoListApplication;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@SpringBootTest(classes = {ToDoListApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TaskPrazoControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void givenUrl_whenSuccessOnGetsResponseAndJsonHasRequiredKV_thenCorrect() {
        get("/api/taskprazo").then().statusCode(200);
    }

    @Test
    public void givenTaskPrazo_whenPostRequestToCreateTask_thenTaskIsCreated() {
        String novaTaskJson = """
                {
                "description": "tarefa de fpaa",
                "priority": "ALTA",
                "dueDays": 12
                }
                """;

        given()
                .contentType("application/json")
                .body(novaTaskJson)
                .when()
                .post("/api/taskprazo")
                .then()
                .statusCode(201)
                .header("Location", notNullValue());
    }

    @Test
    public void givenTaskPrazo_whenPutRequestToUpdateTask_thenTaskIsUpdated() {
        String taskAtualizadaJson = """
                {
                "description": "tarefa de ihc atualizada",
                "priority": "BAIXA",
                "dueDays": 14
                }
                """;

        given()
                .contentType("application/json")
                .body(taskAtualizadaJson)
                .when()
                .put("/api/taskprazo/452")
                .then()
                .statusCode(204);
    }

    @Test
    public void givenTaskPrazoId_whenPutRequestToUpdateStatus_thenStatusIsUpdated() {
        String taskStatusUpdateJson = "{\"completed\":true}";

        given()
                .contentType("application/json")
                .body(taskStatusUpdateJson)
                .when()
                .put("api/taskprazo/502/completed")
                .then()
                .statusCode(204);
    }

    @Test
    public void givenTaskPrazoId_whenDeleteRequestToDeleteTask_thenTaskIsDeleted() {
        given()
                .when()
                .delete("/api/taskprazo/353")
                .then()
                .statusCode(204);
    }
}
