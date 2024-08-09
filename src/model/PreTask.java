package model;

import java.util.Objects;

abstract class PreTask {
    protected String name;
    protected String description;
    protected Status status;
    protected int id;

    public PreTask(String description, String name) {
        this.description = description;
        this.name = name;
        this.status = Status.NEW;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
