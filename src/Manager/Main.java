package Manager;

import Model.Status;
import Model.Task;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        System.out.println(new Task("тестовое описание", Status.IN_PROGRESS, "тестовое название" ));
    }
}
