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
public class TaskControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void givenUrl_whenSuccessOnGetsResponseAndJsonHasRequiredKV_thenCorrect() {
        get("/api/task").then().statusCode(200);
    }

    @Test
    public void givenUrl_whenSuccessOnGetsResponseAndJsonHasOneTask_thenCorrect() {
        get("/api/task/52").then().statusCode(200)
                .assertThat().body("description", equalTo("tarefa teste"));
    }

    @Test
    public void givenTask_whenPostRequestToCreateTask_thenTaskIsCreated() {
        String novaTaskJson = """
                {
                "description": "Segunda tarefa",
                "priority": "ALTA"
                }
                """;

        given()
                .contentType("application/json")
                .body(novaTaskJson)
                .when()
                .post("/api/task")
                .then()
                .statusCode(201)
                .header("Location", notNullValue());
    }

    @Test
    public void givenTask_whenPutRequestToUpdateTask_thenTaskIsUpdated() {
        String taskAtualizadaJson = """
                {
                "description": "Segunda tarefa atualizada",
                "priority": "ALTA"
                }
                """;

        given()
                .contentType("application/json")
                .body(taskAtualizadaJson)
                .when()
                .put("/api/task/202")
                .then()
                .statusCode(204);
    }
}
