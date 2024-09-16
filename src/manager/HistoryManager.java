package manager;

import model.PreTask;
import model.Task;

import java.util.List;

public interface HistoryManager {

    PreTask add(PreTask preTask);

    void remove(int id);

    List<PreTask> getHistory();
}
