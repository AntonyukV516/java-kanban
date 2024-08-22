package managerTest;

import manager.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ManagersTest {

    @Test
    void getDefault(){
        TaskMeneger taskMeneger = new InMemoryTaskManager();

        TaskMeneger actualTaskManager = Managers.getDefault();

        Assertions.assertEquals(taskMeneger.getTasks(), actualTaskManager.getTasks());
        Assertions.assertEquals(taskMeneger.getEpics(), actualTaskManager.getEpics());
        Assertions.assertEquals(taskMeneger.getSubtasks(), actualTaskManager.getSubtasks());
    }

    @Test
    void getDefaultHistory(){
        HistoryManager historyManager = new InMemoryHistoryManager();

        HistoryManager actualHistoryManager = Managers.getDefaultHistory();

        Assertions.assertEquals(historyManager.getHistory(), actualHistoryManager.getHistory());
    }
}
