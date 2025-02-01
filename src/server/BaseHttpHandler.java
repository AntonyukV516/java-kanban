package server;

import com.google.gson.Gson;
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

    protected void writeResponse(HttpExchange exchange,
                                               String responseString,
                                               int responseCode) throws IOException {
        try (OutputStream os = exchange.getResponseBody()) {
            exchange.sendResponseHeaders(responseCode, 0);
            os.write(responseString.getBytes(StandardCharsets.UTF_8));
        }
        exchange.close();
    }

    protected String forPost(InputStream bodyInputStream) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(bodyInputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line).append("\n");
            }
        }
       String body = requestBody.toString().trim();
        Gson gson = new Gson();
        return gson.toJson(body);
    }
}
