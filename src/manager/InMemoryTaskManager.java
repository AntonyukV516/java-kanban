package manager;

import exception.IntersectionTaskException;
import model.*;

import java.time.Instant;
import java.util.*;

public class InMemoryTaskManager implements TaskMeneger {
    private Integer counterId = 0;
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HistoryManager historyManager = Managers.getDefaultHistory();
    private TreeSet<PreTask> prioritizedTasks = new TreeSet<>(Comparator.comparing(PreTask::getStartTime));


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
        if (!isIntersection(epic)) {
            if (epic.getId() == null) {
                epic.setId(genId());
            }
            epics.put(epic.getId(), epic);
            prioritizedTasks.add(epic);
            return epic;
        } else throw new IntersectionTaskException("Эпик пересекается по времени");
    }

    @Override
    public Subtask addSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicId());
        if (!isIntersection(subtask)) {
            if (subtask.getId() == null) {
                subtask.setId(genId());
            }
            if (epic == null) {
                return null;
            } else {
                subtasks.put(subtask.getId(), subtask);
                epic.addSubtaskId(subtask.getId());
                changeEpicStatus(subtask.getEpicId());
                prioritizedTasks.add(subtask);
                epic.setStartTime(epic.getStartTime(getSubTaskFromEpic(epic.getId())));
                epic.setDuration(epic.getDuration(getSubTaskFromEpic(epic.getId())));
                return subtask;
            }
        } else throw new IntersectionTaskException("Подзадача пересекается по времени");
    }

    @Override
    public Task addTask(Task task) {
        if (!isIntersection(task)) {
            if (task.getId() == null) {
                task.setId(genId());
            }
            tasks.put(task.getId(), task);
            prioritizedTasks.add(task);
            return task;
        } else throw new IntersectionTaskException("Задача пересекается по времени");
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
        if (!isIntersection(updatedTask)) {
            Integer id = updatedTask.getId();
            if (tasks.containsKey(id)) {
                tasks.put(id, updatedTask);
                return updatedTask;
            }
            return null;
        } else throw new IntersectionTaskException("Задача пересекается по времени");
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
        if (!isIntersection(updatedSubtask)) {
            int id = updatedSubtask.getId();
            if (subtasks.containsKey(id)) {
                Epic epic = epics.get(updatedSubtask.getEpicId());
                subtasks.put(id, updatedSubtask);
                changeEpicStatus(updatedSubtask.getEpicId());
                epic.setStartTime(epic.getStartTime(getSubTaskFromEpic(epic.getId())));
                epic.setDuration(epic.getDuration(getSubTaskFromEpic(epic.getId())));
                return updatedSubtask;
            }
            return null;
        } else throw new IntersectionTaskException("Подзадача пересекается по времени");
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

    @Override
    public TreeSet<PreTask> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    @Override
    public boolean isIntersection(PreTask preTask1) {
        return getPrioritizedTasks().stream().anyMatch(preTask -> checkIntersection(preTask, preTask1));
    }

    @Override
    public boolean checkIntersection(PreTask preTask1, PreTask preTask2) {
        Instant startTime1 = preTask1.getStartTime();
        Instant endTime1 = preTask1.getEndTime();
        Instant startTime2 = preTask2.getStartTime();
        Instant endTime2 = preTask2.getEndTime();

        if (startTime1 == null || endTime1 == null || startTime2 == null || endTime2 == null) {
            return false;
        }
        return !(startTime1.isBefore(startTime2) && endTime1.isBefore(endTime2))
                || endTime1.equals(startTime2)
                || startTime2.isBefore(endTime1) && startTime1.isBefore(endTime2)
                || startTime1.equals(startTime2) && endTime1.equals(endTime2)
                || startTime1.isBefore(endTime2) && startTime2.isBefore(endTime1)
                || endTime2.equals(startTime1)
                || !(startTime2.isBefore(startTime1) && endTime2.isBefore(endTime2))
                || startTime2.isAfter(startTime1) && endTime2.isBefore(endTime1)
                || startTime1.isAfter(startTime2) && endTime1.isBefore(endTime2);
    }
}
