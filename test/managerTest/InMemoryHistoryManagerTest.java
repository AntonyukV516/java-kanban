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

        for (int i = 0; i < 11; i++) {
            PreTask savedTask = historyManager.add(task);
        }

        Assertions.assertEquals(10, historyManager.getHistory().size());
        Assertions.assertTrue(historyManager.getHistory().contains(task));
    }


}