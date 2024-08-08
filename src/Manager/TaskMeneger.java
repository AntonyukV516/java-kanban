package Manager;

import Model.Epic;
import Model.Status;
import Model.Subtask;
import Model.Task;

import java.util.HashMap;
import java.util.Objects;

public class TaskMeneger {
    Integer counterID = 0;
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Task> tasks = new HashMap<>();

    Epic AddEpic(Epic epic) {
        this.counterID++;
        epic.setId(counterID);
        epics.put(epic.getId(), epic);
        return epic;
    }

    Subtask AddSubtaskToEpic(Epic epic, Subtask subtask) {
        this.counterID++;
        subtask.setId(counterID);
        epic.setSubtasks(subtask.getId(), subtask);
        return subtask;
    }

    Task AddTask(Task task) {
        counterID++;
        task.setId(counterID);
        tasks.put(task.getId(), task);
        return task;
    }

    HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    void clearEpics() {
        epics.clear();
    }

    void clearTask() {
        tasks.clear();
    }

    Epic getEpicToID(Integer ID) {
        if (epics.containsKey(ID)) {
            return epics.get(ID);
        }
        return null;
    }

    Task getTaskToID(Integer ID) {
        if (tasks.containsKey(ID)) {
            return tasks.get(ID);
        }
        return null;
    }

    Subtask getSubTackToID(Integer ID) {
        for (Epic epic : epics.values()) {
            for (Integer id : epic.getSubtasks().keySet()) {
                if (Objects.equals(id, ID)) {
                    return epic.getSubtasks().get(ID);
                }
            }
        }
        return null;
    }

    Task updateTask(Task updatedTask, Status newStatus) {
        Integer id = updatedTask.getId();
        for (Integer taskID : tasks.keySet()) {
            if (id.equals(taskID)) {
                tasks.put(id, updatedTask);
                tasks.get(id).setStatus(newStatus);
                return updatedTask;
            }
        }
        return null;
    }

    Epic updateEpic(Epic updatedEpic) {
        Integer id = updatedEpic.getId();
        for (Integer epicID : epics.keySet()) {
            if (id.equals(epicID)) {
                epics.put(id, updatedEpic);
                return updatedEpic;
            }
        }
        return null;
    }

    Subtask updateSubTask(Subtask updatedSubtask, Status newStatus) {
        Integer id = updatedSubtask.getId();
        boolean isStatusSumDone = true;
        boolean isStatusSumNew = true;
        for (Epic epic : epics.values()) {
            for (Integer ID : epic.getSubtasks().keySet()) {
                if (Objects.equals(ID, id)) {
                    epic.getSubtasks().put(id, updatedSubtask);
                    epic.getSubtasks().get(id).setStatus(newStatus);
                }
                isStatusSumDone &= epic.getSubtasks().get(ID).getStatus() == Status.DONE;
                isStatusSumNew &= epic.getSubtasks().get(ID).getStatus() == Status.NEW;
                if (isStatusSumDone) {
                    epic.setStatus(Status.DONE);
                } else if (isStatusSumNew) {
                    epic.setStatus(Status.NEW);
                } else {
                    epic.setStatus(Status.IN_PROGRESS);
                }
            }
            return epic.getSubtasks().get(id);
        }
        return null;
    }

    void deleteEpicToID(Integer ID) {
        if (epics.containsKey(ID)) {
            epics.remove(ID);
        }
    }

    void deleteTaskToID(Integer ID) {
        if (tasks.containsKey(ID)) {
            tasks.remove(ID);
        }
    }

    void deleteSubTaskToID(Integer ID) {
        for (Epic epic : epics.values()) {
            for (Integer id : epic.getSubtasks().keySet()) {
                if (Objects.equals(id, ID)) {
                    epic.getSubtasks().remove(ID);
                }
            }
        }
    }

    HashMap<Integer, Subtask> getSubTaskFromEpic(Epic epic) {
        return epic.getSubtasks();
    }


}
