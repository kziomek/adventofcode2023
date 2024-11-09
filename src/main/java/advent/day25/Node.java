package advent.day25;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    String name;

    List<Node> connections = new ArrayList<>();

    public Node(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node node = (Node) o;
        return Objects.equals(name, node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "Node{" +
            "name='" + name + '\'' +
            '}';
    }

    public void removeConnection(Node conn) {
        connections.remove(conn);
    }
}
