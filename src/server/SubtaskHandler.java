package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exception.IntersectionTaskException;
import manager.TaskMeneger;
import model.Subtask;

import java.io.IOException;
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
            String response = taskMeneger.getSubtasks()
                    .stream()
                    .map(Subtask::toString)
                    .collect(Collectors.joining("\n"));
            writeResponse(exchange,response,200);
        } else if (method.equals("GET") && path.split("/").length == 3) {
            try {
                String response = taskMeneger
                        .getSubtaskById(Integer.parseInt(path.split("/")[2])).toString();
                writeResponse(exchange, response, 200);
            } catch (NullPointerException e) {
                writeResponse(exchange, "Not Found", 404);
            }
        } else if (method.equals("POST")) {
            Gson gson = new Gson();
            Subtask subtask = gson.fromJson(forPost(exchange.getRequestBody()), Subtask.class);
            try {
                if (subtask.getId() == null) {
                    taskMeneger.addSubtask(subtask);
                    writeResponse(exchange, "Подзадача успешно добавлена", 201);
                } else {
                    taskMeneger.updateSubtask(subtask);
                    writeResponse(exchange, "Подзадача успешно обновлена", 201);
                }
            } catch (IntersectionTaskException e) {
                writeResponse(exchange, "Not Acceptable", 406);
            }
        } else if (method.equals("DELETE")) {
            taskMeneger.deleteSubtaskById(Integer.parseInt(path.split("/")[2]));
            writeResponse(exchange,"Подзадача успешно удалена",201);
        }
    }
}
