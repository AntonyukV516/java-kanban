package model;

import java.time.Duration;
import java.time.Instant;

public class Task extends PreTask {
    public Task(String description, String name) {
        super(description, name);
    }

    public Task(int id, String name, Status status, String description, Instant startTime, Duration duration) {
        super(id, name, status, description, startTime, duration);
    }
}
