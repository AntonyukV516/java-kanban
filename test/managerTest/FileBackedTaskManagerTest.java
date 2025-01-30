package managerTest;

import manager.FileBackedTaskManager;
import manager.TaskMeneger;
import model.Status;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Duration;
import java.time.Instant;


class FileBackedTaskManagerTest {

    /*@Test
    @DisplayName("Сохраненить и загрузкить данные в файл")
    void SaveAndLoadFile() {
        TaskMeneger taskMeneger = Managers.getDefaultFileBackedTaskManager();
        Task task = new Task(5, "name1", Status.IN_PROGRESS, "description1",
                Instant.now(), Duration.ofHours(20));
        Epic epic = new Epic(20, "name2", Status.IN_PROGRESS, "description2",
                Instant.ofEpochSecond(1), Duration.ofHours(1));

        taskMeneger.addTask(task);
        taskMeneger.addEpic(epic);
        TaskMeneger newTaskMeneger = FileBackedTaskManager.loadFromFile("src/resources/data.csv");

        Assertions.assertFalse(newTaskMeneger.getTasks().isEmpty());
        Assertions.assertFalse(newTaskMeneger.getEpics().isEmpty());
        Assertions.assertTrue(newTaskMeneger.getSubtasks().isEmpty());
    }
*/
    @Test
    @DisplayName("Сохранение и загрузка пустого файла")
    void SaveAndLoadEmptyFile() {
        TaskMeneger taskMeneger = new FileBackedTaskManager(new File("src/resources/test.csv"));
        Task task = new Task(5, "name1", Status.IN_PROGRESS, "description1", Instant.now(), Duration.ofHours(10));

        taskMeneger.addTask(task);
        taskMeneger.deleteTaskById(task.getId());
        FileBackedTaskManager.loadFromFile("src/resources/test.csv");

        Assertions.assertTrue(taskMeneger.getSubtasks().isEmpty());
        Assertions.assertTrue(taskMeneger.getEpics().isEmpty());
        Assertions.assertTrue(taskMeneger.getTasks().isEmpty());
    }

   /* @Test
    @DisplayName("Подсчет времени завершения эпика")
    void epicEndTime() {
        TaskMeneger taskMeneger = Managers.getDefault();
        Epic epic = new Epic(20, "name2", Status.NEW, "description2",
                Instant.now(), Duration.ofHours(24));
        Subtask subtask1 = new Subtask(4, "name1", Status.DONE, "description1",
                Instant.ofEpochSecond(9_000_000_00L), Duration.ofHours(24));
        Subtask subtask2 = new Subtask(5, "name1", Status.IN_PROGRESS, "description1",
                Instant.ofEpochSecond(9_000_000_000L).plusSeconds(3600), Duration.ofHours(24));
        subtask1.setEpicId(epic.getId());
        subtask2.setEpicId(epic.getId());
        taskMeneger.addEpic(epic);
        taskMeneger.addSubtask(subtask1);
        taskMeneger.addSubtask(subtask2);

        Instant epicEndTime = epic.getEndTime();

        Assertions.assertEquals(Instant.ofEpochSecond(9_000_090_000L), epicEndTime);
    } */
}