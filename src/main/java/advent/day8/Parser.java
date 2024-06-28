package advent.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    public static Input parse(String path) throws IOException {
        List<String[]> list = Files.readAllLines(Path.of(path))
            .stream()
            .map(line -> line.replaceAll("[=(,)]", "").replace("  ", " "))
            .map(line -> line.split(" "))
            .toList();

        // build nodes
        Map<String, Node> nodes = new HashMap<>();
        for (int i = 2; i < list.size(); i++) {
            Node node = new Node(list.get(i)[0]);
            nodes.put(node.getValue(), node);
        }

        // link nodes
        for (int i = 2; i < list.size(); i++) {
            Node source = nodes.get(list.get(i)[0]);
            Node left = nodes.get(list.get(i)[1]);
            Node right = nodes.get(list.get(i)[2]);

            source.setLeft(left);
            source.setRight(right);
        }

        String instructions = list.get(0)[0];

        return new Input(nodes.get("AAA"), instructions);

    }
}
