package Model;

import java.util.HashMap;

public class Epic extends PreTask {
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    // Реализовано так, так как по смыслу задачи эпик должен иметь подзадачи и знать о них,
    // т.е. они есть часть обьектов  этого класса
    //Иначе нет смысла его использовать - для этого в менеджере хранится таблица обычных задач

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
                ", id=" + id;
    }
}
