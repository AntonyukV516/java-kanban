package manager;

import model.PreTask;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<PreTask> history = new ArrayList<>();
    private int historyMaxSize = 10;


    @Override
    public List<PreTask> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public void add(PreTask preTask){
        if (history.size() < historyMaxSize) {
            history.addLast(preTask);
        } else {
            history.removeFirst();
            history.addLast(preTask);
        }
    }

}
