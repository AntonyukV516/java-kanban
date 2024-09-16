package managerTest;

import manager.HistoryManager;
import manager.Managers;
import model.PreTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryHistoryManagerTest {

    private HistoryManager historyManager;

    @BeforeEach
    void init() {
        this.historyManager = Managers.getDefaultHistory();
    }

    @Test
    void getHistory() {
        Task task = new Task("сесть за комп", "Учеба");
        task.setId(1);

        for (int i = 0; i < 2; i++) {
            PreTask savedTask = historyManager.add(task);
        }

        Assertions.assertEquals(1, historyManager.getHistory().size());
        Assertions.assertTrue(historyManager.getHistory().contains(task));
    }

    @Test
    void remove() {
        Task task = new Task("продолжить отпуск", "Отпуск");
        task.setId(5);

        historyManager.add(task);
        historyManager.remove(5);

        Assertions.assertEquals(0, historyManager.getHistory().size());
    }

    @Test
    void add() {
        Task task1 = new Task("сесть за комп", "Учеба");
        Task task2 = new Task("продолжить отпуск", "Отпуск");
        task1.setId(1);
        task2.setId(2);

        PreTask savedTask1 = historyManager.add(task1);
        PreTask savedTask2 = historyManager.add(task2);
        PreTask savedTask3 = historyManager.add(task1);
        PreTask first = historyManager.getHistory().getFirst();
        PreTask last = historyManager.getHistory().getLast();

        Assertions.assertEquals(2,historyManager.getHistory().size());
        Assertions.assertEquals(task1, savedTask3);
        Assertions.assertEquals(first, savedTask2);
        Assertions.assertEquals(last, savedTask1);
    }
}