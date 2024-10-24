package model;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public abstract class PreTask {
    protected String name;
    protected String description;
    protected Status status;
    protected int id;
    protected Duration duration;
    protected Instant startTime;

    public PreTask(String description, String name) {
        this.description = description;
        this.name = name;
        this.status = Status.NEW;
    }

    public PreTask(int id, String name, Status status, String description, Instant startTime, Duration duration) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return getStartTime().plus(getDuration());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreTask preTask = (PreTask) o;
        return getId() == preTask.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return
                "Название " + name + '\'' +
                        ", Описание '" + description + '\'' +
                        ", Статус " + status +
                        " , id=" + id;
    }
}
