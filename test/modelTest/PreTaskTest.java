package modelTest;

import model.Epic;
import model.PreTask;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PreTaskTest {

    @Test
    void testEquals() {
        PreTask task1 = new Task("111", "name111");
        task1.setId(1);
        PreTask epic1 = new Epic("222","name222");
        epic1.setId(2);
        PreTask subtask1 = new Subtask("333", "name333");
        subtask1.setId(3);

        PreTask task2 = new Task("444", "name444");
        task2.setId(1);
        PreTask epic2 = new Epic("555","name555");
        epic2.setId(2);
        PreTask subtask2 = new Subtask("777", "name777");
        subtask2.setId(3);

        Assertions.assertEquals(task1, task2);
        Assertions.assertEquals(epic1, epic2);
        Assertions.assertEquals(subtask1, subtask2);
    }
}