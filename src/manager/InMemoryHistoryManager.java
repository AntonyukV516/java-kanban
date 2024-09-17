package manager;

import model.PreTask;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
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
        List<PreTask> history = new ArrayList<>();
        for (Node node : nodeMap.values()) {
            history.addLast(node.values);
        }
        return history;
    }

    @Override
    public PreTask add(PreTask preTask) {
        Node oldLast = last;
        Node newNode = new Node(preTask);
        if (oldLast == null) {
            first = newNode;
            last = newNode;
        } else {
            last.previous = oldLast;
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
        Node delitedNode = nodeMap.remove(id);
        removeNode(delitedNode);
    }

    private void removeNode(Node node) {
        Node oldPrev = node.previous;
        Node oldNext = node.next;
        if (node.next == null) {
            oldPrev = last;
        } else {
            oldPrev.next = node.next;
        }
        if (node.previous == null) {
            oldNext = first;
        } else {
            oldPrev.next = node.next;
        }
    }
}
