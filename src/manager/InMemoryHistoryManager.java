package manager;

import model.PreTask;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private List<PreTask> history = new ArrayList<>();
    private Map<Integer, Node> nodeMap = new LinkedHashMap<>();
    private Node first;
    private Node last;

    static class Node {
        private Node previous;
        private Node next;
        private PreTask values;

        public Node(PreTask values) {
            this.previous = null;
            this.next = null;
            this.values = values;
        }
    }


    @Override
    public List<PreTask> getHistory() {
        history.clear();
        for (Node node : nodeMap.values()) {
            history.addLast(node.values);
        }
        return history;
    }

    @Override
    public PreTask add(PreTask preTask) {
        final Node oldLast = last;
        final Node newNode = new Node(preTask);
        last = newNode;
        if (oldLast == null) {
            first = newNode;
        } else {
            oldLast.next = newNode;
        }
        if (nodeMap.containsKey(preTask.getId())) {
            remove(preTask.getId());
        }
        nodeMap.put(preTask.getId(), newNode);
        return preTask;
    }

    @Override
    public void remove(int id) {
        nodeMap.remove(id);
    }
}
