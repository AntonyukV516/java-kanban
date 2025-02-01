package server;

import com.sun.net.httpserver.HttpServer;
import manager.Managers;
import manager.TaskMeneger;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    static TaskMeneger taskMeneger = Managers.getDefault();
    static HttpServer httpTaskServer;

    static {
        try {
            httpTaskServer = HttpServer.create(new InetSocketAddress(8080), 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpTaskServer() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        httpTaskServer.createContext("/tasks", new TaskHandler(taskMeneger));
        httpTaskServer.createContext("/subtasks", new SubtaskHandler(taskMeneger));
        httpTaskServer.createContext("/epics", new EpicHandler(taskMeneger));
        httpTaskServer.createContext("/history", new HistoryHandler(taskMeneger));
        httpTaskServer.createContext("/prioritized", new PrioritizedHandler(taskMeneger));
        httpTaskServer.start();
    }

    public TaskMeneger getTaskMeneger() {
        return taskMeneger;
    }

    public void start() {
        httpTaskServer.start();
    }

    public void stop() {
        httpTaskServer.stop(1);
    }

}
