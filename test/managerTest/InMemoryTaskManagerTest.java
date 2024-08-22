package managerTest;

import manager.Managers;
import manager.TaskMeneger;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class InMemoryTaskManagerTest {

    private TaskMeneger taskMeneger;

    @BeforeEach
    void init() {
        this.taskMeneger = Managers.getDefault();
    }


    @Test
    void addEpic() {
        Epic epic = new Epic("навести уборку", "Порядок");
        Epic expectedEpic = new Epic("навести уборку", "Порядок");

        Epic savedEpic = taskMeneger.addEpic(epic);

        Assertions.assertNotNull(savedEpic);
        Assertions.assertNotNull(savedEpic.getId());
        Assertions.assertEquals(expectedEpic, savedEpic);
    }


    @Test
    void addSubtask() {
        Epic epic = new Epic("навести уборку", "Порядок");
        Subtask subtask1 = new Subtask("помыть посуду", "посуда");
        Subtask subtask2 = new Subtask("помыть посуду", "посуда");
        subtask1.setEpicID(epic.getId());
        subtask2.setEpicID(epic.getId());
        Subtask expectedSubtask = new Subtask("помыть посуду", "посуда");
        expectedSubtask.setId(1);

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
    void addTask(){
        Task task = new Task("111", "name1");
        Task expectedTask = new Task("111", "name1");

        Task savedTask = taskMeneger.addTask(task);

        Assertions.assertEquals(expectedTask, savedTask);
    }

    @Test
    void clearEpics() {
        Epic epic1 = new Epic("навести уборку", "Порядок");

        taskMeneger.addEpic(epic1);
        taskMeneger.clearEpics();

        Assertions.assertTrue(taskMeneger.getEpics().isEmpty());
    }

    @Test
    void clearTasks(){
        Task task = new Task("111", "name");

        taskMeneger.addTask(task);
        taskMeneger.clearTask();

        Assertions.assertTrue(taskMeneger.getTasks().isEmpty());
    }

    @Test
    void clearSubtasks(){
        Subtask subtask = new Subtask("222", "name");

        taskMeneger.addSubtask(subtask);
        taskMeneger.clearSubtask();

        Assertions.assertTrue(taskMeneger.getSubtasks().isEmpty());
    }

    @Test
    void getEpicByID(){
        Epic epic = new Epic("123", " name123");
        Epic expectedEpic = new Epic("123", " name123");

        taskMeneger.addEpic(epic);
        Epic actualEpic = taskMeneger.getEpicByID(epic.getId());

        Assertions.assertEquals(expectedEpic, actualEpic);
    }

    @Test
    void  getSubtaskByID(){
        Epic epic = new Epic("444", " name444");
        taskMeneger.addEpic(epic);
        Subtask subtask = new Subtask("999", "name999");
        Subtask expectedSubtask = new Subtask("999", "name999");
        subtask.setEpicID(epic.getId());
        expectedSubtask.setId(1);

        taskMeneger.addSubtask(subtask);
        Subtask actualSubtask = taskMeneger.getSubtaskByID(subtask.getId());

        Assertions.assertEquals(expectedSubtask, actualSubtask);
    }

    @Test
    void getTaskByID() {
        Task task = new Task("попить чай", "Чай");
        Task expectedTask = new Task("попить чай", "Чай");

        taskMeneger.addTask(task);
        Task actualTask = taskMeneger.getTaskByID(task.getId());

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
    }

    @Test
    void deleteSubtaskByID() {
        Epic epic = new Epic("уборка кухни", "Уборка");
        Subtask subtask = new Subtask("помыть плиту", "Плита");
        subtask.setEpicID(epic.getId());
        taskMeneger.addSubtask(subtask);

        taskMeneger.deleteSubtaskByID(subtask.getId());

        Assertions.assertTrue(epic.getSubtasksID().isEmpty());
    }

    @Test
    void getSubTaskFromEpic() {
        Epic epic = new Epic("поработать", "Работа");
        taskMeneger.addEpic(epic);
        Subtask subtask1 = new Subtask("доехать", "Проезд");
        Subtask subtask2 = new Subtask("не уснуть", "Работа");
        subtask1.setEpicID(epic.getId());
        subtask2.setEpicID(epic.getId());
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
    void savedOldVersionTaskInHistory(){
        Task task = new Task("description", "Name");
        Task updatedTask = new Task("descriptionNew", "NameNew");
        Task expectedTask = new Task("description", "Name");

        taskMeneger.addTask(task);
        taskMeneger.getTaskByID(task.getId());
        updatedTask.setId(task.getId());
        taskMeneger.updateTask(updatedTask);
        taskMeneger.getTaskByID(updatedTask.getId());

        Assertions.assertTrue(taskMeneger.getHistory().contains(expectedTask));
    }

    @Test
    void consistencyTask(){
        Task task = new Task("описание", "Имя");

        Task actualTask = taskMeneger.addTask(task);

        Assertions.assertEquals(task.getId(), actualTask.getId());
        Assertions.assertEquals(task.getName(), actualTask.getName());
        Assertions.assertEquals(task.getDescription(), actualTask.getDescription());
        Assertions.assertEquals(task.getStatus(), actualTask.getStatus());
    }

}