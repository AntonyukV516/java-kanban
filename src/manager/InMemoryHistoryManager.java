package manager;

import model.PreTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private Map<Integer, Node> nodeMap = new HashMap<>();
    private Node first;
    private Node last;

    static class Node {
        private Node previous;
        private Node next;
        private PreTask value;

        public Node(PreTask value) {
            this.previous = null;
            this.next = null;
            this.value = value;
        }
    }

    @Override
    public List<PreTask> getHistory() {
        List<PreTask> history = new ArrayList<>();
        Node node = first;
        while (node != null) {
            history.add(node.value);
            node = node.next;
        }
        return history;
    }

    @Override
    public PreTask add(PreTask preTask) {
        if (nodeMap.containsKey(preTask.getId())) {
            remove(preTask.getId());
        }
        Node node = linkLast(preTask);
        nodeMap.put(preTask.getId(), node);
        return preTask;
    }

    @Override
    public void remove(int id) {
        removeNode(id);
    }

    private Node linkLast(PreTask preTask) {
        Node newNode = new Node(preTask);
        if (first == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        return last;
    }

    private void removeNode(int id) {
        Node node = nodeMap.get(id);
        if (node != null) {
            nodeMap.remove(id);
            if (node.previous == null) {
                first = node.next;
            } else {
                node.previous.next = node.next;
            }
            if (node.next == null) {
                last = node.previous;
            } else {
                node.next.previous = node.previous;
            }
            node.previous = null;
            node.next = null;
        }
    }
}
