package server;


import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exception.IntersectionTaskException;
import manager.TaskMeneger;
import model.Task;

import java.io.IOException;
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
            String response = taskMeneger.getTasks()
                    .stream()
                    .map(Task::toString)
                    .collect(Collectors.joining("\n"));
            writeResponse(exchange, response, 200);
        } else if (method.equals("GET") && path.split("/").length == 3) {
            try {
                String response = taskMeneger
                        .getTaskById(Integer.parseInt(path.split("/")[2])).toString();
                writeResponse(exchange, response, 200);
            } catch (NullPointerException e) {
                writeResponse(exchange, "Not Found", 404);
            }
        } else if (method.equals("POST")) {
            Gson gson = new Gson();
            Task task = gson.fromJson(forPost(exchange.getRequestBody()), Task.class);
            try {
                if (task.getId() == null) {
                    taskMeneger.addTask(task);
                    writeResponse(exchange, "Задача успешно добавлена", 201);
                } else {
                    taskMeneger.updateTask(task);
                    writeResponse(exchange, "Задача успешно обновлена", 201);
                }
            } catch (IntersectionTaskException e) {
                writeResponse(exchange, "Not Acceptable", 406);
            }
        } else if (method.equals("DELETE")) {
            taskMeneger.deleteTaskById(Integer.parseInt(path.split("/")[2]));
            writeResponse(exchange, "Задача успешно удалена", 201);
        }
    }
}
