package manager;

public class Managers  {

     public static TaskMeneger getDefault(){
         return new InMemoryTaskManager();
    }

    public  static  HistoryManager getDefaultHistory(){
         return new InMemoryHistoryManager();
    }
}
