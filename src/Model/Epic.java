package Model;

import java.util.HashMap;

public class Epic extends  PreTask {
    HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public Epic(String description, String name) {
        super(description, name);
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(Integer ID, Subtask subtasks) {
        this.subtasks.put(ID, subtasks);
    }

    @Override
    public String toString() {
        return "Имя " + name + '\'' +
                ", Описание '" + description + '\'' +
                ", подзадача " + subtasks +
                ", статус" + status +
                "id=" + id;
    }
}
