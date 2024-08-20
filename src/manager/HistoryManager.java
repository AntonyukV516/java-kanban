package manager;

import model.PreTask;

import java.util.ArrayList;

public interface HistoryManager {

    void add(PreTask preTask);

    ArrayList<PreTask> getHistory();
}
