package model;

public class Subtask extends PreTask {
    private Integer epicId;

    public Subtask(String description, String name) {
        super(description, name);
    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicID(Integer epicId) {
        this.epicId = epicId;
    }
}
