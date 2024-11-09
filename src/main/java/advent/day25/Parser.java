package advent.day25;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Parser {

    public static List<Node> parse(String path) throws IOException {
        List<String> strings = Files.readAllLines(Path.of(path));

        Map<String, Node> dict = new HashMap<>();
        for (String string : strings) {
            String[] split = string.split(":");
            String name1 = split[0];
            Node node1 = getNode(dict, name1);

            String[] conns = split[1].trim().split(" ");
            for (String conn : conns) {
                Node node2 = getNode(dict, conn);
                node1.connections.add(node2);
                node2.connections.add(node1);
                System.out.println(conn + " " + name1);
            }
        }

//        printGraph(dict.values());
        return new ArrayList<>(dict.values());
    }

    public static void printGraph(Collection<Node> nodes) {
        for (Node node : nodes) {
            for (Node connection : node.connections) {
                System.out.print(node.name);
                System.out.print(" ");
                System.out.print(connection.name);
                System.out.println();
            }
        }
        System.out.println();
    }

    private static Node getNode(Map<String, Node> dict, String name1) {
        Node node1;
        if (dict.containsKey(name1)) {
            node1 = dict.get(name1);
        } else {
            node1 = new Node(name1);
            dict.put(name1, node1);
        }
        return node1;
    }
}
