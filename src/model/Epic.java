package model;

import java.util.ArrayList;

public class Epic extends PreTask {
    private ArrayList<Integer> subtasksID = new ArrayList<>();

    public Epic(String description, String name) {
        super(description, name);
    }

    public ArrayList<Integer> getSubtasksID() {
        return subtasksID;
    }

    public void clearSubtasksID(){
        subtasksID.clear();
    }

    public void removeSubtaskID (Integer ID){
        subtasksID.remove(ID);
    }

    public void addSubtaskID(Integer newSubtasksID) {
        subtasksID.add(newSubtasksID);
    }
}
