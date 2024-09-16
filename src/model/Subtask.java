package model;

public class Subtask extends PreTask {
    private Integer epicId;

    public Subtask(String description, String name) {
        super(description, name);
    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }
}
