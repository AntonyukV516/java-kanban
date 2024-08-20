package manager;

import model.PreTask;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private  ArrayList<PreTask> history = new ArrayList<>();
    private int historyMaxSize = 10;


    @Override
    public ArrayList<PreTask> getHistory() {
        return history;
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
