package model;

public class Task extends PreTask {
    public Task(String description, String name) {
        super(description, name);
    }

    public Task(int id, String name, Status status, String description) {
        super(id, name, status, description);
    }
}
