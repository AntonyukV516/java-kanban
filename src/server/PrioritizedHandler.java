package server;

import com.sun.net.httpserver.HttpExchange;
import manager.TaskMeneger;
import model.PreTask;

import java.io.IOException;
import java.util.stream.Collectors;

public class PrioritizedHandler extends BaseHttpHandler {

    public PrioritizedHandler(TaskMeneger taskMeneger) {
        super(taskMeneger);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if (method.equals("GET")) {
            String response = taskMeneger
                    .getPrioritizedTasks()
                    .stream()
                    .map(PreTask::toString)
                    .collect(Collectors.joining("\n"));
            writeResponse(exchange, response, 200);
        }
    }
}
