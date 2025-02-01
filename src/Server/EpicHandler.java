package Server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exception.IntersectionTaskException;
import manager.TaskMeneger;
import model.Epic;
import model.Subtask;

import java.io.IOException;
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
            String response = taskMeneger.getEpics()
                    .stream()
                    .map(Epic::toString)
                    .collect(Collectors.joining("\n"));
            writeResponse(exchange, response, 200);
        } else if (method.equals("GET") && path.split("/").length == 3) {
            try {
                String response = taskMeneger
                        .getEpicById(Integer.parseInt(path.split("/")[2])).toString();
                writeResponse(exchange, response, 200);
            } catch (NullPointerException e) {
                writeResponse(exchange,"Not Found", 404);
            }
        } else if (method.equals("GET") && path.split("/").length == 4) {
            try {
                String response = taskMeneger
                        .getSubTaskFromEpic(Integer.parseInt(path.split("/")[2]))
                        .stream()
                        .map(Subtask::toString)
                        .collect(Collectors.joining("\n"));
                writeResponse(exchange, response, 200);
            } catch (NullPointerException e) {
                writeResponse(exchange, "Not Found", 404);
            }
        } else if (method.equals("POST")) {
            Gson gson = new Gson();
            Epic epic = gson.fromJson(forPost(exchange.getRequestBody()), Epic.class);
            try {
                if (epic.getId() == null) {
                    taskMeneger.addEpic(epic);
                    writeResponse(exchange, "Эпик успешно добавлен", 201);
                } else {
                    taskMeneger.updateEpic(epic);
                    writeResponse(exchange, "Эпик успешно обновлен", 201);
                }
            } catch (IntersectionTaskException e) {
                writeResponse(exchange, "Not Acceptable", 406);
            }
        } else if (method.equals("DELETE")) {
            taskMeneger.deleteEpicById(Integer.parseInt(path.split("/")[2]));
            writeResponse(exchange, "Эпик успешно удален", 201);
        }
    }
}
