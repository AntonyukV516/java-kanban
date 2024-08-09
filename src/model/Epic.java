package model;

import java.util.ArrayList;

public class Epic extends PreTask {
    private ArrayList<Integer> SubtasksID = new ArrayList<>();

    public Epic(String description, String name) {
        super(description, name);
    }

    public ArrayList<Integer> getSubtasksID() {
        return SubtasksID;
    }

    public void addSubtaskID(Integer subtasksID) {
        SubtasksID.add(subtasksID);
    }
}
