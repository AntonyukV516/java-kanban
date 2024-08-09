package model;

public class Subtask extends PreTask {
    private Integer epicID;

    public Subtask(String description, String name) {
        super(description, name);
    }

    public Integer getEpicID() {
        return epicID;
    }

    public void setEpicID(Integer epicID) {
        this.epicID = epicID;
    }
}
