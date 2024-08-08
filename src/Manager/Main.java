package Manager;

import Model.Epic;
import Model.Status;
import Model.Subtask;

public class Main {

    public static void main(String[] args) {
        //Весь main для проверки методов и их работы, понимаю что
        // этого быть не должно, но для помощи в проверке оставлю
        // я понимаю что это заготовка дальнейших работ
        Epic epic1 = new Epic("навести уборку", "Порядок");
        Epic epic2 = new Epic("отдохнуть хорошо", "Отдых");
        Subtask subtask1 = new Subtask("помыть посуду", "посуда");
        Subtask subtask2 = new Subtask("вынести мусор", "мусор");
        TaskMeneger manager = new TaskMeneger();
        manager.AddEpic(epic1);
        manager.AddEpic(epic2);
        System.out.println(manager.getEpics());
        manager.AddSubtaskToEpic(epic1, subtask1);
        manager.AddSubtaskToEpic(epic1, subtask2);
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubTackToID(4));
        manager.updateSubTask(subtask1, Status.NEW);
        manager.updateSubTask(subtask2, Status.NEW);
        System.out.println(manager.getEpicToID(1));


    }
}
