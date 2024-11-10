package advent.day25;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class BFS {

    public static int size(List<Node> nodes) {
        int counter = 0;
        Set<Node> seen = new HashSet<>();
        Node firstNode = nodes.getFirst();
        Queue<Node> queue = new LinkedList<>();
        queue.add(firstNode);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (seen.contains(node)) {
                continue;
            }
            counter++;
            seen.add(node);
            queue.addAll(node.connections);
        }
        return counter;
    }

    public static List<Node> farthestPath(Node startNode) {
        System.out.println("Start Node name: " + startNode.name);
        Map<Node, Node> previous = new HashMap<>();
        Set<Node> seen = new HashSet<>();
        seen.add(startNode);
        Node lastElement = null;
        Queue<Node> queue = new LinkedList<>();
        queue.add(startNode);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            lastElement = node;
            for (Node connection : node.connections) {
                if (!seen.contains(connection)) {
                    queue.add(connection);
                    seen.add(connection);
                    previous.put(connection, node);
                }
            }
        }
        List<Node> path = new ArrayList<>();
        Node current = lastElement;
        while (!current.equals(startNode)) {
            path.add(current);
            current = previous.get(current);
        }
        path.add(startNode);

        return path;
    }
}
