package Model;

import java.util.Objects;

 abstract class PreTask {
    String name;
    String description;
    String status;

    public PreTask(String description, String status, String name) {
        this.description = description;
        this.status = status;
        this.name = name;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreTask preTask = (PreTask) o;
        return Objects.equals(getName(), preTask.getName()) &&
                Objects.equals(getDescription(), preTask.getDescription()) &&
                Objects.equals(getStatus(), preTask.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getStatus());
    }
}
