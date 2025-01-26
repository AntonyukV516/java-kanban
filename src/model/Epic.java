package model;

import manager.TaskMeneger;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Epic extends PreTask {
    private List<Integer> subtasksId = new ArrayList<>();

    public Epic(String description, String name) {
        super(description, name);
    }

    public Epic(int id, String name, Status status, String description, Instant startTime, Duration duration) {
        super(id, name, status, description, startTime, duration);
    }

    public List<Integer> getSubtasksId() {
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

    public Instant getEndTime(TaskMeneger taskMeneger) {
        try {
            return taskMeneger.getSubTaskFromEpic(id)
                    .stream()
                    .map(subtask -> subtask.getStartTime().plus(duration))
                    .max(Comparator.comparing(Instant::getEpochSecond))
                    .orElseGet(() -> startTime.plus(duration));
        } catch (RuntimeException e) {
            return startTime.plus(duration);
        }
    }

    public Duration getDuration(TaskMeneger taskMeneger) {
        return Duration.between(getStartTime(taskMeneger), getEndTime(taskMeneger));
    }

    public Instant getStartTime(TaskMeneger taskMeneger) {
        try {
            return taskMeneger.getSubTaskFromEpic(this.id)
                    .stream()
                    .map(subtask -> subtask.getStartTime().plus(duration))
                    .min(Comparator.comparing(Instant::getEpochSecond))
                    .orElseGet(() -> startTime);
        } catch (RuntimeException e) {
            return startTime;
        }

    }

  /*  @Override
    public Instant getEndTime() {
//Думаю проблема в том, что компаратор treeSet  не может корректно обработать время начала эпика
но я не понимаю как это скоректировать
    }

    @Override
    public Instant getStartTime() {

    }

    @Override
    public Duration getDuration() {

    }
*/

}
