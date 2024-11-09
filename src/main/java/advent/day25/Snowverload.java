package advent.day25;

import java.io.IOException;
import java.util.List;

/**
 * Path for selecting a node and it's farther node won't go through bridge for every pair
 * But we can find two pair of these nodes.
 * Then we can remove path completely (bridge and other paths)
 * I think it works because 3 edges bridge is min cut.
 */
public class Snowverload {
    public static void main(String[] args) throws IOException {
//        List<Node> nodes = Parser.parse("src/main/resources/day25/example.txt");
                                List<Node> nodes = Parser.parse("src/main/resources/day25/my-input.txt");

        int size = BFS.size(nodes);
        System.out.println("size: " + size);

        for (int i = 0; i < 3; i++) {
            findAndRemovePath(nodes.get(1));
        }

        int size1 = BFS.size(nodes);
        System.out.println("Component size " + size1);
        if (size1 < size) {
            System.out.println("Result " + size1 * (size - size1));
        } else {
            System.out.println("Solution not found for selected start node");
        }
    }

    private static void findAndRemovePath(Node node) {
        List<Node> farthestPath = BFS.farthestPath(node);
        System.out.println(farthestPath);
        removePath(farthestPath);
    }

    private static void removePath(List<Node> nodes) {
        for (int i = 0; i < nodes.size() - 2; i++) {
            Node n1 = nodes.get(i);
            Node n2 = nodes.get(i + 1);
            n1.removeConnection(n2);
            n2.removeConnection(n1);
        }
    }
}
