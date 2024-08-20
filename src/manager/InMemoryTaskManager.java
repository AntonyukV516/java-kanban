package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskMeneger {
    private Integer counterID = 1;
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HistoryManager historyManager = new InMemoryHistoryManager();


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

    @Override
    public Epic addEpic(Epic epic) {
        epic.setId(genID());
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public Subtask addSubtask(Subtask subtask) {
        subtask.setId(genID());
        if (epics.get(subtask.getEpicID()) == null) {
            return null;
        } else {
            subtasks.put(subtask.getId(), subtask);
            epics.get(subtask.getEpicID()).addSubtaskID(subtask.getId());
            changeEpicStatus(subtask.getEpicID());
            return subtask;
        }
    }

    @Override
    public Task addTask(Task task) {
        task.setId(genID());
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void clearEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void clearTask() {
        tasks.clear();
    }

    @Override
    public void clearSubtask() {
        for (Epic epic : epics.values()) {
            epic.clearSubtasksID();
            epic.setStatus(Status.NEW);
        }
        subtasks.clear();
    }

    @Override
    public Epic getEpicByID(Integer ID) {
        if (epics.containsKey(ID)) {
            historyManager.add(epics.get(ID));
            return epics.get(ID);
        }
        return null;
    }

    @Override
    public Task getTaskByID(Integer ID) {
        if (tasks.containsKey(ID)) {
            historyManager.add(tasks.get(ID));
            return tasks.get(ID);
        }
        return null;
    }

    @Override
    public Subtask getSubtaskByID(Integer ID) {
        if (subtasks.containsKey(ID)) {
            historyManager.add(subtasks.get(ID));
            return subtasks.get(ID);
        }
        return null;
    }

    @Override
    public Task updateTask(Task updatedTask) {
        Integer id = updatedTask.getId();
        if (tasks.containsKey(id)) {
            tasks.put(id, updatedTask);
            return updatedTask;
        }
        return null;
    }

    @Override
    public Epic updateEpic(Epic updatedEpic) {
        Integer id = updatedEpic.getId();
        if (epics.containsKey(id)) {
            String updatedName = updatedEpic.getName();
            String updatedDiscription = updatedEpic.getDescription();
            Epic oldEpic = epics.get(id);
            oldEpic.setDescription(updatedDiscription);
            oldEpic.setName(updatedName);
            return oldEpic;
        }
        return null;
    }

    @Override
    public Subtask updateSubtask(Subtask updatedSubtask) {
        int id = updatedSubtask.getId();
        if (subtasks.containsKey(id)) {
            subtasks.put(id, updatedSubtask);
            changeEpicStatus(updatedSubtask.getEpicID());
            return updatedSubtask;
        }
        return null;
    }

    @Override
    public void deleteEpicByID(Integer ID) {
        Epic deletingEpic = epics.get(ID);
        for (Integer id : deletingEpic.getSubtasksID()) {
            subtasks.remove(id);
        }
        epics.remove(ID);
    }

    @Override
    public void deleteTaskByID(Integer ID) {
        if (tasks.containsKey(ID)) {
            tasks.remove(ID);
        }
    }

    @Override
    public void deleteSubtaskByID(Integer ID) {
        if (subtasks.containsKey(ID)) {
            Integer epicID = subtasks.get(ID).getEpicID();
            epics.get(epicID).removeSubtaskID(ID);
            subtasks.remove(ID);
            changeEpicStatus(epicID);
        }
    }

    @Override
    public ArrayList<Subtask> getSubTaskFromEpic(Integer epicID) {
        ArrayList<Subtask> subtasksFromEpic = new ArrayList<>();
        if (epics.get(epicID) == null) {
            return null;
        } else {
            for (Integer id : epics.get(epicID).getSubtasksID()) {
                subtasksFromEpic.add(subtasks.get(id));
            }
            return subtasksFromEpic;
        }
    }


}
