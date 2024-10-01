package manager;

import model.PreTask;

import java.util.List;

public interface HistoryManager {

    PreTask add(PreTask preTask);

    void remove(int id);

    List<PreTask> getHistory();
}
