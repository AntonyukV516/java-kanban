package manager;

import model.Epic;
import model.PreTask;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

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

    Epic getEpicById(Integer id);

    Task getTaskById(Integer id);

    Subtask getSubtaskById(Integer id);

    Task updateTask(Task updatedTask);

    Epic updateEpic(Epic updatedEpic);

    Subtask updateSubtask(Subtask updatedSubtask);

    void deleteEpicById(Integer id);

    void deleteTaskById(Integer id);

    void deleteSubtaskById(Integer id);

    ArrayList<Subtask> getSubTaskFromEpic(Integer epicId);

    List<PreTask> getHistory();
}
