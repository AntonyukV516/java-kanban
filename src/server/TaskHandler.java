package server;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import exception.IntersectionTaskException;
import manager.DurationAdapter;
import manager.InstantAdapter;
import manager.TaskMeneger;
import model.Task;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Collectors;

public class TaskHandler extends BaseHttpHandler {

    public TaskHandler(TaskMeneger taskMeneger) {
        super(taskMeneger);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        if (method.equals("GET") && path.split("/").length == 2) {
            getTasks(exchange);
        } else if (method.equals("GET") && path.split("/").length == 3) {
            getTaskById(exchange, path);
        } else if (method.equals("POST")) {
            postTask(exchange);
        } else if (method.equals("DELETE")) {
           deleteById(exchange, path);
        }
    }

    private void getTasks(HttpExchange exchange) throws IOException {
        String response = taskMeneger.getTasks()
                .stream()
                .map(Task::toString)
                .collect(Collectors.joining("\n"));
        writeResponse(exchange, response, 200);
    }

    private void getTaskById(HttpExchange exchange, String path) throws IOException {
        try {
            String response = taskMeneger
                    .getTaskById(Integer.parseInt(path.split("/")[2])).toString();
            writeResponse(exchange, response, 200);
        } catch (NullPointerException e) {
            writeNotFound(exchange);
        }
    }

    private void postTask(HttpExchange exchange) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(Instant.class, new InstantAdapter())
                .create();
        String req = forPost(exchange.getRequestBody());
        Task task = gson.fromJson(req, Task.class);
        try {
            if (taskMeneger.getTasks().contains(task)) {
                taskMeneger.updateTask(task);
                writeResponse(exchange, "Задача успешно обновлена", 201);
            } else {
                taskMeneger.addTask(task);
                writeResponse(exchange, "Задача успешно добавлена", 201);
            }
        } catch (IntersectionTaskException e) {
            writeIntersection(exchange);
        }
    }

    private void deleteById(HttpExchange exchange, String path) throws IOException {
        taskMeneger.deleteTaskById(Integer.parseInt(path.split("/")[2]));
        writeResponse(exchange, "Задача успешно удалена", 201);
    }
}

