package manager;

import model.PreTask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<PreTask> history = new ArrayList<>();
    private final static int HISTORY_MAX_SIZE = 10;


    @Override
    public List<PreTask> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public PreTask add(PreTask preTask){
        if (history.size() < HISTORY_MAX_SIZE) {
            history.addLast(preTask);
            return preTask;
        } else {
            history.removeFirst();
            history.addLast(preTask);
            return preTask;
        }
    }

}
