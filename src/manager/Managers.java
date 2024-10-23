package manager;

import java.io.File;

public class Managers  {

     public static TaskMeneger getDefault() {
         return new InMemoryTaskManager();
    }

    public  static  HistoryManager getDefaultHistory() {
         return new InMemoryHistoryManager();
    }

    public static TaskMeneger getDefaultFileBackedTaskManager() {
         return new FileBackedTaskManager(new File("src/resources/data.csv"));
    }
}
