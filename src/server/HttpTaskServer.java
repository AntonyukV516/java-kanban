package server;

import com.sun.net.httpserver.HttpServer;
import manager.Managers;
import manager.TaskMeneger;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final TaskMeneger taskMeneger = Managers.getDefault();
    private static final HttpServer httpTaskServer;

    static {
        try {
            httpTaskServer = HttpServer.create(new InetSocketAddress(8080), 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        httpTaskServer.createContext("/tasks", new TaskHandler(taskMeneger));
        httpTaskServer.createContext("/subtasks", new SubtaskHandler(taskMeneger));
        httpTaskServer.createContext("/epics", new EpicHandler(taskMeneger));
        httpTaskServer.createContext("/history", new HistoryHandler(taskMeneger));
        httpTaskServer.createContext("/prioritized", new PrioritizedHandler(taskMeneger));
        httpTaskServer.start();
    }
}
