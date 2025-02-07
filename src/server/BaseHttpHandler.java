package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskMeneger;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class BaseHttpHandler implements HttpHandler {
    TaskMeneger taskMeneger;

    public BaseHttpHandler(TaskMeneger taskMeneger) {
        this.taskMeneger = taskMeneger;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
    }

    public void writeResponse(HttpExchange exchange,
                                               String responseString,
                                               int responseCode) throws IOException {
        try (OutputStream os = exchange.getResponseBody()) {
            exchange.sendResponseHeaders(responseCode, 0);
            os.write(responseString.getBytes(StandardCharsets.UTF_8));
        }
        exchange.close();
    }

    public String forPost(InputStream bodyInputStream) throws IOException {
        return new String(bodyInputStream.readAllBytes(), StandardCharsets.UTF_8).trim();
    }

    public void writeNotFound(HttpExchange exchange) throws IOException {
        writeResponse(exchange,"Not Found",404);
    }

    public void writeIntersection(HttpExchange exchange) throws IOException {
        writeResponse(exchange, "Not Acceptable", 406);
    }
}
