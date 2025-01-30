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

    public Instant getEndTime(ArrayList<Subtask> subTaskFromEpic) {
        try {
            return  subTaskFromEpic
                    .stream()
                    .map(subtask -> subtask.getStartTime().plus(duration))
                    .max(Comparator.comparing(Instant::getEpochSecond))
                    .orElseGet(() -> startTime.plus(duration));
        } catch (RuntimeException e) {
            return startTime.plus(duration);
        }
    }

    public Duration getDuration(ArrayList<Subtask> subTaskFromEpic) {
        return Duration.between(getStartTime(subTaskFromEpic), getEndTime(subTaskFromEpic));
    }

    public  Instant getStartTime(ArrayList<Subtask> subTaskFromEpic) {
        try {
            return subTaskFromEpic
                    .stream()
                    .map(subtask -> subtask.getStartTime().plus(getDuration()))
                    .min(Comparator.comparing(Instant::getEpochSecond))
                    .orElseGet(() -> startTime);
        } catch (RuntimeException e) {
            return startTime;
        }

    }
}
