package Manager;

import Model.Epic;
import Model.Status;
import Model.Subtask;

public class Main {

    public static void main(String[] args) {
        Epic epic1 = new Epic("навести уборку", "Порядок");
        Epic epic2 = new Epic("отдохнуть хорошо", "Отдых");
        Subtask subtask1 = new Subtask("помыть посуду", "посуда");
        Subtask subtask2 = new Subtask("вынести мусор", "мусор");
        TaskMeneger manager = new TaskMeneger();
        manager.AddEpic(epic1);
        manager.AddEpic(epic2);
        System.out.println(manager.epics);
        manager.AddSubtaskToEpic(epic1, subtask1);
        manager.AddSubtaskToEpic(epic1, subtask2);
        System.out.println(manager.epics);
        //System.out.println(manager.getSubTackToID(4));
        manager.updateSubTask(subtask1, Status.NEW);
        manager.updateSubTask(subtask2, Status.NEW);
        System.out.println(manager.getEpicToID(1));


    }
}
