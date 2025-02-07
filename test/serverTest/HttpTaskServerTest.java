
package serverTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import manager.DurationAdapter;
import manager.InstantAdapter;
import manager.Managers;
import manager.TaskMeneger;
import model.Status;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class HttpTaskServerTest {
    private HttpServer httpTaskServer;
    private TaskMeneger taskMeneger;

    @BeforeEach
    void init() throws IOException {
        httpTaskServer = HttpServer.create(new InetSocketAddress(8080), 0);
        taskMeneger = Managers.getDefault();
        httpTaskServer.createContext("/tasks", new TaskHandler(taskMeneger));
        httpTaskServer.createContext("/subtasks", new SubtaskHandler(taskMeneger));
        httpTaskServer.createContext("/epics", new EpicHandler(taskMeneger));
        httpTaskServer.createContext("/history", new HistoryHandler(taskMeneger));
        httpTaskServer.createContext("/prioritized", new PrioritizedHandler(taskMeneger));
        httpTaskServer.start();
    }

    @AfterEach
    void stop() {
        httpTaskServer.stop(1);
    }

    @Test
    @DisplayName("Тест на добавление задачи")
    void addTask() {
        Task task = new Task(1, "Test 2",
                Status.NEW, "Testing task 2", Instant.now(), Duration.ofSeconds(5));
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(Instant.class, new InstantAdapter())
                .create();
        String taskJson = gson.toJson(task);

        try (HttpClient client = HttpClient.newHttpClient()) {
            URI url = URI.create("http://localhost:8080/tasks");
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                    .uri(url)
                    .version(HttpClient.Version.HTTP_1_1)
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse.BodyHandler<String> handler = HttpResponse
                    .BodyHandlers.ofString(StandardCharsets.UTF_8);
            try {
                HttpResponse<String> response = client.send(request, handler);

                assertEquals(201, response.statusCode());
                List<Task> tasksFromManager = taskMeneger.getTasks();

                assertNotNull(tasksFromManager, "Задачи не возвращаются");
                assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
                assertEquals("Test 2", tasksFromManager.getFirst().getName(), "Некорректное имя задачи");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    @DisplayName("Тест вывод задач")
    void getTasks() {
        try (HttpClient client = HttpClient
                .newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build()) {
            URI url = URI.create("http://localhost:8080/tasks");
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .uri(url)
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                assertEquals(200, response.statusCode());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
