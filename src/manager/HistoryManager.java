package manager;

import model.PreTask;
import model.Task;

import java.util.List;

public interface HistoryManager {

    PreTask add(PreTask preTask);

    List<PreTask> getHistory();
}
