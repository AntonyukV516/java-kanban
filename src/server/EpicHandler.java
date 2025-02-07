package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import exception.IntersectionTaskException;
import manager.DurationAdapter;
import manager.InstantAdapter;
import manager.TaskMeneger;
import model.Epic;
import model.Subtask;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Collectors;

public class EpicHandler extends BaseHttpHandler {

    public EpicHandler(TaskMeneger taskMeneger) {
        super(taskMeneger);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        if (method.equals("GET") && path.split("/").length == 2) {
           getEpics(exchange);
        } else if (method.equals("GET") && path.split("/").length == 3) {
           getEpicById(exchange, path);
        } else if (method.equals("GET") && path.split("/").length == 4) {
           getSubtasksFromEpic(exchange, path);
        } else if (method.equals("POST")) {
            postEpic(exchange);
        } else if (method.equals("DELETE")) {
            deleteById(exchange, path);
        }
    }

    private void getEpics(HttpExchange exchange) throws IOException {
        String response = taskMeneger.getEpics()
                .stream()
                .map(Epic::toString)
                .collect(Collectors.joining("\n"));
        writeResponse(exchange, response, 200);
    }

    private void getEpicById(HttpExchange exchange, String path) throws IOException {
        try {
            String response = taskMeneger
                    .getEpicById(Integer.parseInt(path.split("/")[2])).toString();
            writeResponse(exchange, response, 200);
        } catch (NullPointerException e) {
           writeNotFound(exchange);
        }
    }

    private void getSubtasksFromEpic(HttpExchange exchange, String path) throws IOException {
        try {
            String response = taskMeneger
                    .getSubTaskFromEpic(Integer.parseInt(path.split("/")[2]))
                    .stream()
                    .map(Subtask::toString)
                    .collect(Collectors.joining("\n"));
            writeResponse(exchange, response, 200);
        } catch (NullPointerException | IOException e) {
            writeNotFound(exchange);
        }
    }

    private void postEpic(HttpExchange exchange) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(Instant.class, new InstantAdapter())
                .create();
        String req = forPost(exchange.getRequestBody());
        Epic epic = gson.fromJson(req, Epic.class);
        try {
            if (taskMeneger.getEpics().contains(epic)) {
                taskMeneger.updateEpic(epic);
                writeResponse(exchange, "Эпик успешно обновлен", 201);
            } else {
                taskMeneger.addEpic(epic);
                writeResponse(exchange, "Эпик успешно добавлен", 201);
            }
        } catch (IntersectionTaskException e) {
            writeIntersection(exchange);
        }
    }

    private void deleteById(HttpExchange exchange, String path) throws IOException {
        taskMeneger.deleteEpicById(Integer.parseInt(path.split("/")[2]));
        writeResponse(exchange, "Эпик успешно удален", 201);
    }
}
