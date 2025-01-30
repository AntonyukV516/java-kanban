package managerTest;

import manager.Managers;
import manager.TaskMeneger;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

class InMemoryTaskManagerTest {

    private TaskMeneger taskMeneger;

    @BeforeEach
    void init() {
        this.taskMeneger = Managers.getDefault();
    }


    @Test
    void addEpic() {
        Epic epic = new Epic(20, "name2", Status.NEW, "description2",
                Instant.now(), Duration.ofHours(24));
        Epic expectedEpic = new Epic(20, "name2", Status.NEW, "description2",
                Instant.now(), Duration.ofHours(24));

        Epic savedEpic = taskMeneger.addEpic(epic);

        Assertions.assertNotNull(savedEpic);
        Assertions.assertNotNull(savedEpic.getId());
        Assertions.assertEquals(expectedEpic, savedEpic);
    }


    /*@Test
    void addSubtask() {
        Epic epic = new Epic(25, "name2", Status.NEW, "description2",
                Instant.ofEpochSecond(9_000_000L), Duration.ofHours(1));
        taskMeneger.addEpic(epic);
        Subtask subtask1 = new Subtask(10, "name1", Status.NEW, "description1",
                Instant.ofEpochSecond(9_000_000_000L), Duration.ofHours(1));
        taskMeneger.addSubtask(subtask1);
        Subtask subtask2 = new Subtask(15, "name", Status.NEW, "description",
                Instant.ofEpochSecond(9_000L), Duration.ofHours(1));
        taskMeneger.addSubtask(subtask2);
        subtask1.setEpicId(epic.getId());
        subtask2.setEpicId(epic.getId());
        Subtask expectedSubtask = new Subtask(10, "name1", Status.NEW, "description1",
                Instant.ofEpochSecond(9_000_000_000L), Duration.ofHours(1));

        Epic savedEpic = taskMeneger.addEpic(epic);
        Subtask savedSubtask = taskMeneger.addSubtask(subtask1);
        subtask2.setStatus(Status.DONE);
        taskMeneger.addSubtask(subtask2);

        Assertions.assertNotNull(savedSubtask);
        Assertions.assertNotNull(savedSubtask.getId());
        Assertions.assertEquals(expectedSubtask, savedSubtask);
        Assertions.assertEquals(Status.IN_PROGRESS, savedEpic.getStatus());
    }

    @Test
    void addTask() {
        Task task = new Task("111", "name1");
        Task expectedTask = new Task("111", "name1");

        Task savedTask = taskMeneger.addTask(task);

        Assertions.assertEquals(expectedTask, savedTask);
    }

    @Test
    void clearEpics() {
        Epic epic1 = new Epic("навести уборку", "Порядок");
        Epic epic2 = new Epic("111", "111");
        epic1.setId(1);
        epic2.setId(2);

        taskMeneger.getEpicById(epic1.getId());
        taskMeneger.getEpicById(epic2.getId());
        taskMeneger.addEpic(epic1);
        taskMeneger.clearEpics();

        Assertions.assertTrue(taskMeneger.getEpics().isEmpty());
    }

    @Test
    void clearTasks() {
        Task task = new Task("111", "name");
        Task task1 = new Task("222", "name");
        task.setId(1);
        task1.setId(2);

        taskMeneger.getTaskById(task.getId());
        taskMeneger.getTaskById(task1.getId());
        taskMeneger.addTask(task);
        taskMeneger.clearTask();

        Assertions.assertTrue(taskMeneger.getTasks().isEmpty());
    }*/

    @Test
    void clearSubtasks() {
        Subtask subtask = new Subtask("222", "name");

        taskMeneger.addSubtask(subtask);
        taskMeneger.clearSubtask();

        Assertions.assertTrue(taskMeneger.getSubtasks().isEmpty());
    }
/*
    @Test
    void getEpicById() {
        Epic epic = new Epic("123", " name123");
        Epic expectedEpic = new Epic("123", " name123");

        taskMeneger.addEpic(epic);
        Epic actualEpic = taskMeneger.getEpicById(epic.getId());

        Assertions.assertEquals(expectedEpic, actualEpic);
    }

    @Test
    void getSubtaskById() {
        Epic epic = new Epic(1, "name3", Status.DONE, "description3",
                Instant.ofEpochSecond(10), Duration.ofHours(1));
        taskMeneger.addEpic(epic);
        Subtask subtask = new Subtask(10, "name3", Status.DONE, "description3",
                Instant.ofEpochSecond(50000), Duration.ofHours(1));
        Subtask expectedSubtask = new Subtask(10, "name3", Status.DONE, "description3",
                Instant.ofEpochSecond(50000), Duration.ofHours(1));
        subtask.setEpicId(epic.getId());

        taskMeneger.addSubtask(subtask);
        Subtask actualSubtask = taskMeneger.getSubtaskById(subtask.getId());

        Assertions.assertEquals(expectedSubtask, actualSubtask);
    }

    @Test
    void getTaskById() {
        Task task = new Task("попить чай", "Чай");
        Task expectedTask = new Task("попить чай", "Чай");

        taskMeneger.addTask(task);
        Task actualTask = taskMeneger.getTaskById(task.getId());

        Assertions.assertEquals(expectedTask, actualTask);
    }


    @Test
    void updateTask() {
        Task task = new Task("пойти спать", "Сон");
        Task savedTask = taskMeneger.addTask(task);
        Task updatedTask = new Task("Больше спать ", "Сон_новый");
        updatedTask.setId(savedTask.getId());
        Task expectedUpdatedTask = new Task("Больше спать ", "Сон_новый");

        Task actualUpdatedTask = taskMeneger.updateTask(updatedTask);

        Assertions.assertEquals(expectedUpdatedTask, actualUpdatedTask);
    } */

    @Test
    void deleteSubtaskById() {
        Epic epic = new Epic("уборка кухни", "Уборка");
        Subtask subtask = new Subtask("помыть плиту", "Плита");
        subtask.setEpicId(epic.getId());
        taskMeneger.addSubtask(subtask);

        taskMeneger.deleteSubtaskById(subtask.getId());

        Assertions.assertTrue(epic.getSubtasksId().isEmpty());
    }
/*
    @Test
    void getSubTaskFromEpic() {
        Epic epic = new Epic(50, "name", Status.DONE, "description",
                Instant.ofEpochSecond(1), Duration.ofHours(1));
        taskMeneger.addEpic(epic);
        Subtask subtask1 = new Subtask(10, "name3", Status.DONE, "description3",
                Instant.ofEpochSecond(50000), Duration.ofHours(1));
        Subtask subtask2 = new Subtask(3, "name3", Status.DONE, "description3",
                Instant.ofEpochSecond(9_000_000L), Duration.ofHours(1));
        subtask1.setEpicId(epic.getId());
        subtask2.setEpicId(epic.getId());
        taskMeneger.addSubtask(subtask1);
        taskMeneger.addSubtask(subtask2);
        ArrayList<Subtask> expectedList = new ArrayList<>();
        expectedList.add(subtask1);
        expectedList.add(subtask2);

        ArrayList<Subtask> actualList = taskMeneger.getSubTaskFromEpic(epic.getId());

        Assertions.assertEquals(expectedList.getFirst(), actualList.getFirst());
        Assertions.assertEquals(expectedList.getLast(), actualList.getLast());
    }

    @Test
    void savedOldVersionTaskInHistory() {
        Task task = new Task("description", "Name");
        Task updatedTask = new Task("descriptionNew", "NameNew");
        Task expectedTask = new Task("description", "Name");

        taskMeneger.addTask(task);
        taskMeneger.getTaskById(task.getId());
        updatedTask.setId(task.getId());
        taskMeneger.updateTask(updatedTask);
        taskMeneger.getTaskById(updatedTask.getId());

        Assertions.assertTrue(taskMeneger.getHistory().contains(expectedTask));
    }

    @Test
    void consistencyTask() {
        Task task = new Task("описание", "Имя");

        Task actualTask = taskMeneger.addTask(task);

        Assertions.assertEquals(task.getId(), actualTask.getId());
        Assertions.assertEquals(task.getName(), actualTask.getName());
        Assertions.assertEquals(task.getDescription(), actualTask.getDescription());
        Assertions.assertEquals(task.getStatus(), actualTask.getStatus());
    }

    @Test
    @DisplayName("Получение отсортированного по времени начала списка всех задач")
    void getPrioritizedTasksTest() {
        Epic epic = new Epic(20, "name2", Status.NEW, "description2",
                Instant.ofEpochSecond(9_000_000_000L), Duration.ofHours(24));
        Subtask subtask = new Subtask(4, "name1", Status.DONE, "description1",
                Instant.ofEpochSecond(8_000_000_000L), Duration.ofHours(24));
        Task task = new Task(1, "name3", Status.DONE, "description3",
                Instant.ofEpochSecond(7_000_000_000L), Duration.ofHours(10));
        subtask.setEpicId(epic.getId());

        taskMeneger.addEpic(epic);
        taskMeneger.addSubtask(subtask);
        taskMeneger.addTask(task);
        TreeSet<PreTask> prioritizedTasks = taskMeneger.getPrioritizedTasks();

        Assertions.assertEquals(task, prioritizedTasks.getFirst());
        Assertions.assertEquals(epic, prioritizedTasks.getLast());
    }

    @Test
    @DisplayName("Проверка пересечений по времени")
    void isIntersectionTest() {
        Epic epic = new Epic(20, "name2", Status.NEW, "description2",
                Instant.ofEpochSecond(9_000_000_000L), Duration.ofHours(1));
        Task task = new Task(1, "name3", Status.DONE, "description3",
                Instant.ofEpochSecond(10), Duration.ofHours(1));

        taskMeneger.addTask(task);
        taskMeneger.addEpic(epic);
        boolean taskIsIntersection = taskMeneger.isIntersection(task);
        boolean epicIsIntersection = taskMeneger.isIntersection(epic);

        Assertions.assertFalse(taskIsIntersection);
        Assertions.assertFalse(epicIsIntersection);
    }*/
}