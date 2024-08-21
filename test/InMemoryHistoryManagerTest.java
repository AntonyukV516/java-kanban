import manager.HistoryManager;
import manager.Managers;
import manager.TaskMeneger;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryHistoryManagerTest {

    private HistoryManager historyManager;
    private TaskMeneger taskMeneger;

    @BeforeEach
    void init() {
        this.historyManager = Managers.getDefaultHistory();
        this.taskMeneger = Managers.getDefault();
    }

    @Test
    void getHistory() {
        Task task = new Task("сесть за комп", "Учеба");
        taskMeneger.addTask(task);

        Task savedTask1 = taskMeneger.getTaskByID(task.getId());
        Task savedTask2 = taskMeneger.getTaskByID(task.getId());
        Task savedTask3 = taskMeneger.getTaskByID(task.getId());
        Task savedTask4 = taskMeneger.getTaskByID(task.getId());
        Task savedTask5 = taskMeneger.getTaskByID(task.getId());
        Task savedTask6 = taskMeneger.getTaskByID(task.getId());
        Task savedTask7 = taskMeneger.getTaskByID(task.getId());
        Task savedTask8 = taskMeneger.getTaskByID(task.getId());
        Task savedTask9 = taskMeneger.getTaskByID(task.getId());
        Task savedTask10 = taskMeneger.getTaskByID(task.getId());
        Task savedTask11 = taskMeneger.getTaskByID(task.getId());

        Assertions.assertEquals(10, taskMeneger.getHistoryManager().getHistory().size());
    }

}