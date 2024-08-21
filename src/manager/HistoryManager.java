package manager;

import model.PreTask;

import java.util.List;

public interface HistoryManager {

    void add(PreTask preTask);

    List<PreTask> getHistory();
}
