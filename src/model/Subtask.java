package model;

import java.time.Duration;
import java.time.Instant;

public class Subtask extends PreTask {
    private Integer epicId;

    public Subtask(String description, String name) {
        super(description, name);
    }

    public Subtask(int id, String name, Status status, String description, Instant startTime, Duration duration) {
        super(id, name, status, description, startTime, duration);
    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }
}
