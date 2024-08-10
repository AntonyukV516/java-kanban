package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskMeneger {
    private Integer counterID = 1;
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private Integer genID() {
        return counterID++;
    }

    private void changeEpicStatus(Integer epicID) {
        boolean isStatusSumDone = true;
        boolean isStatusSumNew = true;
        Epic epic = epics.get(epicID);
        Status result = null;
        for (Subtask subtask : getSubTaskFromEpic(epicID)) {
            if (isStatusSumDone &= subtask.getStatus() == Status.DONE) {
                result = Status.DONE;
            } else if (isStatusSumNew &= subtask.getStatus() == Status.NEW) {
                result = Status.NEW;
            } else {
                result = Status.IN_PROGRESS;
            }
        }
        epic.setStatus(result);
    }

    public Epic addEpic(Epic epic) {
        epic.setId(genID());
        epics.put(epic.getId(), epic);
        return epic;
    }

    public Subtask addSubtask(Subtask subtask) {
        subtask.setId(genID());
        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getEpicID()).addSubtaskID(subtask.getId());
        changeEpicStatus(subtask.getEpicID());
        return subtask;
    }

    public Task addTask(Task task) {
        task.setId(genID());
        tasks.put(task.getId(), task);
        return task;
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void clearEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void clearTask() {
        tasks.clear();
    }

    public void clearSubtask() {
        for (Epic epic : epics.values()) {
            epic.clearSubtasksID();
            epic.setStatus(Status.NEW);
        }
        subtasks.clear();
    }

    public Epic getEpicByID(Integer ID) {
        if (epics.containsKey(ID)) {
            return epics.get(ID);
        }
        return null;
    }

    public Task getTaskByID(Integer ID) {
        if (tasks.containsKey(ID)) {
            return tasks.get(ID);
        }
        return null;
    }

    public Subtask getSubTackByID(Integer ID) {
        if (subtasks.containsKey(ID)) {
            return subtasks.get(ID);
        }
        return null;
    }

    public Task updateTask(Task updatedTask) {
        Integer id = updatedTask.getId();
        for (Integer taskID : tasks.keySet()) {
            if (id.equals(taskID)) {
                tasks.put(id, updatedTask);
                return updatedTask;
            }
        }
        return null;
    }

    public Epic updateEpic(Epic updatedEpic) {
        Integer id = updatedEpic.getId();
        for (Integer epicID : epics.keySet()) {
            if (id.equals(epicID)) {
                String updatedName = updatedEpic.getName();
                String updatedDiscription = updatedEpic.getDescription();
                Epic oldEpic = epics.get(id);
                oldEpic.setDescription(updatedDiscription);
                oldEpic.setName(updatedName);
                return oldEpic;
            }
        }
        return null;
    }

    public Subtask updateSubtask(Subtask updatedSubtask) {
        int id = updatedSubtask.getId();
        for (Integer subtaskID : subtasks.keySet()) {
            if (id == subtaskID) {
                subtasks.put(id, updatedSubtask);
                changeEpicStatus(updatedSubtask.getEpicID());
                return updatedSubtask;
            }
        }
        return null;
    }

    public void deleteEpicByID(Integer ID) {
        Epic deletingEpic = epics.get(ID);
        for (Integer id : deletingEpic.getSubtasksID()){
            subtasks.remove(id);
        }
            epics.remove(ID);
    }

    public void deleteTaskByID(Integer ID) {
        if (tasks.containsKey(ID)) {
            tasks.remove(ID);
        }
    }

    public void deleteSubTaskByID(Integer ID) {
        if (subtasks.containsKey(ID)) {
            Integer epicID = subtasks.get(ID).getEpicID();
            subtasks.remove(ID);
            changeEpicStatus(epicID);
        }
    }

    public ArrayList<Subtask> getSubTaskFromEpic(Integer epicID) {
        ArrayList<Subtask> subtasksFromEpic = new ArrayList<>();
        for (Integer id : epics.get(epicID).getSubtasksID()){
            subtasksFromEpic.add(subtasks.get(id));
        }
        return subtasksFromEpic;
    }


}
