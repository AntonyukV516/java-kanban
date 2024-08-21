package manager;

import model.Epic;
import model.PreTask;
import model.Subtask;
import model.Task;

import java.util.ArrayList;

public interface TaskMeneger {
    Epic addEpic(Epic epic);

    Subtask addSubtask(Subtask subtask);

    Task addTask(Task task);

    ArrayList<Epic> getEpics();

    ArrayList<Task> getTasks();

    ArrayList<Subtask> getSubtasks();

    void clearEpics();

    void clearTask();

    void clearSubtask();

    Epic getEpicByID(Integer ID);

    Task getTaskByID(Integer ID);

    Subtask getSubtaskByID(Integer ID);

    Task updateTask(Task updatedTask);

    Epic updateEpic(Epic updatedEpic);

    Subtask updateSubtask(Subtask updatedSubtask);

    void deleteEpicByID(Integer ID);

    void deleteTaskByID(Integer ID);

    void deleteSubtaskByID(Integer ID);

    ArrayList<Subtask> getSubTaskFromEpic(Integer epicID);

    HistoryManager getHistoryManager();
}
