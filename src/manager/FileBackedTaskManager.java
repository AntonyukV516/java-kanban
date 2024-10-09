package manager;

import exception.FileReadException;
import exception.FileSaveException;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.io.*;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private File file = new File("data.csv");

    @Override
    public Epic addEpic(Epic epic) {
        super.addEpic(epic);
        save();
        return epic;
    }

    @Override
    public Subtask addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
        return subtask;
    }

    @Override
    public Task addTask(Task task) {
        super.addTask(task);
        save();
        return task;
    }

    @Override
    public void clearEpics() {
        super.clearEpics();
        save();
    }

    @Override
    public void clearTask() {
        super.clearTask();
        save();
    }

    @Override
    public void clearSubtask() {
        super.clearSubtask();
        save();
    }

    @Override
    public Task updateTask(Task updatedTask) {
        super.updateTask(updatedTask);
        save();
        return updatedTask;
    }

    @Override
    public Epic updateEpic(Epic updatedEpic) {
        super.updateEpic(updatedEpic);
        save();
        return updatedEpic;
    }

    @Override
    public Subtask updateSubtask(Subtask updatedSubtask) {
        super.updateSubtask(updatedSubtask);
        save();
        return updatedSubtask;
    }

    @Override
    public void deleteEpicById(Integer id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteTaskById(Integer id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteSubtaskById(Integer id) {
        super.deleteSubtaskById(id);
        save();
    }

    private void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            List<Epic> epics = getEpics();
            bw.write("id,type,name,status,description,epic\n");
            for (Epic epic : epics) {
                String epicAsString = String.format("%d,EPIC,%s,%s,%s,%d\n", epic.getId(), epic.getName(),
                        epic.getStatus(), epic.getDescription(), -1);
                bw.write(epicAsString);
            }
            List<Task> tasks = getTasks();
            for (Task task : tasks) {
                String taskAsString = String.format("%d,TASK,%s,%s,%s,%d\n", task.getId(), task.getName(),
                        task.getStatus(), task.getDescription(), -1);
                bw.write(taskAsString);
            }
            List<Subtask> subtasks = getSubtasks();
            for (Subtask subtask : subtasks) {
                String subtaskAsString = String.format("%d,SUBTASK,%s,%s,%s,%d\n", subtask.getId(), subtask.getName(),
                        subtask.getStatus(), subtask.getDescription(), subtask.getEpicId());
                bw.write(subtaskAsString);
            }
        } catch (IOException e) {
            throw new FileSaveException("Ошибка при записи в файл");
        }
    }

    public static TaskMeneger loadFromFile(String path) {
        TaskMeneger taskMeneger = new FileBackedTaskManager();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while (br.ready()) {
                String line = br.readLine();
                String[] contents = line.split(",");
                switch (contents[1]) {
                    case "EPIC" -> {
                        Epic epic = new Epic(Integer.parseInt(contents[0]), contents[2],
                                Status.valueOf(contents[3]), contents[4]);
                        taskMeneger.addEpic(epic);
                    }
                    case "TASK" -> {
                        Task task = new Task(Integer.parseInt(contents[0]), contents[2],
                                Status.valueOf(contents[3]), contents[4]);
                        taskMeneger.addTask(task);
                    }
                    case "SUBTASK" -> {
                        Subtask subtask = new Subtask(Integer.parseInt(contents[0]), contents[2],
                                Status.valueOf(contents[3]), contents[4]);
                        subtask.setEpicId(Integer.parseInt(contents[5]));
                        taskMeneger.addSubtask(subtask);
                    }
                }
            }
        } catch (IOException e) {
            throw new FileReadException("Ошибка при чтении из файла");
        }
        return taskMeneger;
    }
}

