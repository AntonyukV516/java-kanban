package manager;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskMeneger {
    private Integer counterId = 0;
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HistoryManager historyManager = Managers.getDefaultHistory();


    private Integer genId() {
        return counterId++;
    }

    private void changeEpicStatus(Integer epicId) {
        boolean isStatusSumDone = true;
        boolean isStatusSumNew = true;
        Epic epic = epics.get(epicId);
        Status result = null;
        for (Subtask subtask : getSubTaskFromEpic(epicId)) {
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
        epic.setId(genId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public Subtask addSubtask(Subtask subtask) {
        subtask.setId(genId());
        if (epics.get(subtask.getEpicId()) == null) {
            return null;
        } else {
            subtasks.put(subtask.getId(), subtask);
            epics.get(subtask.getEpicId()).addSubtaskId(subtask.getId());
            changeEpicStatus(subtask.getEpicId());
            return subtask;
        }
    }

    @Override
    public Task addTask(Task task) {
        task.setId(genId());
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
        for (Integer id : epics.keySet()) {
            historyManager.remove(id);
        }
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void clearTask() {
        for (Integer id : tasks.keySet()) {
            historyManager.remove(id);
        }
        tasks.clear();
    }

    @Override
    public void clearSubtask() {
        for (Epic epic : epics.values()) {
            epic.clearSubtasksId();
            epic.setStatus(Status.NEW);
        }
        for (Integer id : subtasks.keySet()) {
            historyManager.remove(id);
        }
        subtasks.clear();
    }

    @Override
    public Epic getEpicById(Integer id) {
        if (epics.containsKey(id)) {
            historyManager.add(epics.get(id));
            return epics.get(id);
        }
        return null;
    }

    @Override
    public Task getTaskById(Integer id) {
        if (tasks.containsKey(id)) {
            historyManager.add(tasks.get(id));
            return tasks.get(id);
        }
        return null;
    }

    @Override
    public Subtask getSubtaskById(Integer id) {
        if (subtasks.containsKey(id)) {
            historyManager.add(subtasks.get(id));
            return subtasks.get(id);
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
            changeEpicStatus(updatedSubtask.getEpicId());
            return updatedSubtask;
        }
        return null;
    }

    @Override
    public void deleteEpicById(Integer id) {
        if (epics.containsKey(id)) {
            Epic deletingEpic = epics.get(id);
            for (Integer idEach : deletingEpic.getSubtasksId()) {
                subtasks.remove(idEach);
            }
            historyManager.remove(id);
            epics.remove(id);
        }
    }

    @Override
    public void deleteTaskById(Integer id) {
        if (tasks.containsKey(id)) {
            historyManager.remove(id);
            tasks.remove(id);
        }
    }

    @Override
    public void deleteSubtaskById(Integer id) {
        if (subtasks.containsKey(id)) {
            Integer epicId = subtasks.get(id).getEpicId();
            epics.get(epicId).removeSubtaskId(id);
            historyManager.remove(id);
            subtasks.remove(id);
            changeEpicStatus(epicId);
        }
    }

    @Override
    public ArrayList<Subtask> getSubTaskFromEpic(Integer epicId) {
        ArrayList<Subtask> subtasksFromEpic = new ArrayList<>();
        if (epics.get(epicId) == null) {
            return null;
        } else {
            for (Integer id : epics.get(epicId).getSubtasksId()) {
                subtasksFromEpic.add(subtasks.get(id));
            }
            return subtasksFromEpic;
        }
    }

    @Override
    public List<PreTask> getHistory() {
        return historyManager.getHistory();
    }
}
