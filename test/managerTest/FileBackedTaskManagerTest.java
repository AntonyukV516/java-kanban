package managerTest;

import manager.FileBackedTaskManager;
import manager.TaskMeneger;
import model.Epic;
import model.Status;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class FileBackedTaskManagerTest {

    @Test
    void SaveAndLoadFile() {
        TaskMeneger taskMeneger = new FileBackedTaskManager();
        Task task = new Task(5, "name1", Status.IN_PROGRESS, "description1");
        Epic epic = new Epic(20, "name2", Status.IN_PROGRESS, "description2");

        taskMeneger.addTask(task);
        taskMeneger.addEpic(epic);
        TaskMeneger newTaskMeneger = FileBackedTaskManager.loadFromFile("data.csv");

        Assertions.assertFalse(newTaskMeneger.getTasks().isEmpty());
        Assertions.assertFalse(newTaskMeneger.getEpics().isEmpty());
        Assertions.assertTrue(newTaskMeneger.getSubtasks().isEmpty());
    }
}