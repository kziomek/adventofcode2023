package advent.day25;

import java.io.IOException;
import java.util.List;

/**
 * Path for selecting a node and it's farther node won't go through bridge for every pair
 * But we can find two pair of these nodes.
 * Then we can remove path completely (bridge and other paths)
 * I think it works because 3 edges bridge is min cut.
 * <p>
 * Implementation is modification of Ford-Fulkerson algorithm to calculate maximum flow.
 * In fact it's pretty much Edmonds-Karp algorithm as it's selecting shortest path between source and sink using BFS algorithm.
 * https://en.wikipedia.org/wiki/Edmonds%E2%80%93Karp_algorithm
 * https://www.youtube.com/watch?v=RppuJYwlcI8&ab_channel=WilliamFiset
 * <p>
 * Visualising graphs
 * https://csacademy.com/app/graph_editor/
 * <p>
 * Other most likely good choice is Karger's algorithm. Also, potentially Stoer–Wagner algorithm.
 */
public class Snowverload {
    public static void main(String[] args) throws IOException {
        //        List<Node> nodes = Parser.parse("src/main/resources/day25/example.txt");
        List<Node> nodes = Parser.parse("src/main/resources/day25/my-input.txt");

        int graphSize = BFS.size(nodes);
        System.out.println("Graph size: " + graphSize);

        for (int i = 0; i < 3; i++) {
            // if it does not remove minimum cut edges, we need to pick another start node, but selected one works for my input and example input
            findAndRemovePath(nodes.get(1));
        }

        int componentSize = BFS.size(nodes);
        System.out.println("Component size " + componentSize);
        if (componentSize < graphSize) {
            System.out.println("Result " + componentSize * (graphSize - componentSize));
        } else {
            System.out.println("Solution not found for selected start node");
        }
    }

    private static void findAndRemovePath(Node node) {
        List<Node> farthestPath = BFS.farthestPath(node);
        System.out.println("Shortest path to farthest node: " + farthestPath);
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
