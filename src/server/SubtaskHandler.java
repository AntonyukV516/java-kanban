package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import exception.IntersectionTaskException;
import manager.DurationAdapter;
import manager.InstantAdapter;
import manager.TaskMeneger;
import model.Subtask;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Collectors;

public class SubtaskHandler extends BaseHttpHandler {

    public SubtaskHandler(TaskMeneger taskMeneger) {
        super(taskMeneger);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        if (method.equals("GET") && path.split("/").length == 2) {
            getSubtasks(exchange);
        } else if (method.equals("GET") && path.split("/").length == 3) {
            getSubtaskById(exchange, path);
        } else if (method.equals("POST")) {
            postSubtask(exchange);
        } else if (method.equals("DELETE")) {
            deleteById(exchange, path);
        }
    }

        private void getSubtasks(HttpExchange exchange) throws IOException {
            String response = taskMeneger.getSubtasks()
                    .stream()
                    .map(Subtask::toString)
                    .collect(Collectors.joining("\n"));
            writeResponse(exchange,response,200);
        }

        private void getSubtaskById(HttpExchange exchange, String path) throws IOException {
            try {
                String response = taskMeneger
                        .getSubtaskById(Integer.parseInt(path.split("/")[2])).toString();
                writeResponse(exchange, response, 200);
            } catch (NullPointerException e) {
                writeNotFound(exchange);
            }
        }

        private void postSubtask(HttpExchange exchange) throws IOException {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Duration.class, new DurationAdapter())
                    .registerTypeAdapter(Instant.class, new InstantAdapter())
                    .create();
            String req = forPost(exchange.getRequestBody());
            Subtask subtask = gson.fromJson(req, Subtask.class);
            try {
                if (taskMeneger.getSubtasks().contains(subtask)) {
                    taskMeneger.updateSubtask(subtask);
                    writeResponse(exchange, "Подзадача успешно обновлена", 201);
                } else {
                    taskMeneger.addSubtask(subtask);
                    writeResponse(exchange, "Подзадача успешно добавлена", 201);
                }
            } catch (IntersectionTaskException e) {
                writeIntersection(exchange);
            }
        }

        private void deleteById(HttpExchange exchange, String path) throws IOException {
            taskMeneger.deleteSubtaskById(Integer.parseInt(path.split("/")[2]));
            writeResponse(exchange, "Подзадача успешно удалена", 201);
        }
}
