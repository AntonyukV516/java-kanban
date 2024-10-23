package managerTest;

import manager.FileBackedTaskManager;
import manager.Managers;
import manager.TaskMeneger;
import model.Epic;
import model.Status;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;


class FileBackedTaskManagerTest {

    @Test
    @DisplayName("Сохраненить и загрузкить данные в файл")
    void SaveAndLoadFile() {
        TaskMeneger taskMeneger = Managers.getDefaultFileBackedTaskManager();
        Task task = new Task(5, "name1", Status.IN_PROGRESS, "description1");
        Epic epic = new Epic(20, "name2", Status.IN_PROGRESS, "description2");

        taskMeneger.addTask(task);
        taskMeneger.addEpic(epic);
        TaskMeneger newTaskMeneger = FileBackedTaskManager.loadFromFile("src/resources/data.csv");

        Assertions.assertFalse(newTaskMeneger.getTasks().isEmpty());
        Assertions.assertFalse(newTaskMeneger.getEpics().isEmpty());
        Assertions.assertTrue(newTaskMeneger.getSubtasks().isEmpty());
    }

    @Test
    @DisplayName("Сохранение и загрузка пустого файла")
    void SaveAndLoadEmptyFile() {
        TaskMeneger taskMeneger = new FileBackedTaskManager(new File("src/resources/test.csv"));
        Task task = new Task(5, "name1", Status.IN_PROGRESS, "description1");

        taskMeneger.addTask(task);
        taskMeneger.deleteTaskById(task.getId());
        FileBackedTaskManager.loadFromFile("src/resources/test.csv");

        Assertions.assertTrue(taskMeneger.getSubtasks().isEmpty());
        Assertions.assertTrue(taskMeneger.getEpics().isEmpty());
        Assertions.assertTrue(taskMeneger.getTasks().isEmpty());
    }
}