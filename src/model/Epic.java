package model;

import java.util.ArrayList;

public class Epic extends PreTask {
    private ArrayList<Integer> subtasksId = new ArrayList<>();

    public Epic(String description, String name) {
        super(description, name);
    }

    public Epic(int id, String name, Status status, String description) {
        super(id, name, status, description);
    }

    public ArrayList<Integer> getSubtasksId() {
        return subtasksId;
    }

    public void clearSubtasksId() {
        subtasksId.clear();
    }

    public void removeSubtaskId(Integer id) {
        subtasksId.remove(id);
    }

    public void addSubtaskId(Integer newSubtasksId) {
        subtasksId.add(newSubtasksId);
    }
}
