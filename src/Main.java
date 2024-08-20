import manager.InMemoryTaskManager;
import model.Epic;
import model.Status;
import model.Subtask;

public class Main {

    public static void main(String[] args) {
        //Весь main для проверки методов и их работы, понимаю что
        // этого быть не должно, но для помощи в проверке оставлю
        // я понимаю что это заготовка дальнейших работ
        Epic epic1 = new Epic("навести уборку", "Порядок");
        Epic epic2 = new Epic("отдохнуть хорошо", "Отдых");
        Subtask subtask1 = new Subtask("помыть посуду", "посуда");
        Subtask subtask2 = new Subtask("вынести мусор", "мусор");
        InMemoryTaskManager manager = new InMemoryTaskManager();
        manager.addEpic(epic1);
        manager.addEpic(epic2);
        System.out.println(manager.getEpics());
        subtask1.setEpicID(1); ;
        subtask2.setEpicID(1);
        manager.addSubtask( subtask1);
        manager.addSubtask( subtask2);
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtaskByID(4));
        manager.updateSubtask(subtask1);
        manager.updateSubtask(subtask2);
        System.out.println(manager.getEpicByID(2));
        System.out.println(manager.getSubTaskFromEpic(1));
        subtask2.setStatus(Status.DONE);
        subtask1.setStatus(Status.NEW);
        manager.updateSubtask(subtask2);
        System.out.println(manager.getSubTaskFromEpic(1));
        System.out.println(manager.getEpicByID(1));


    }
}
