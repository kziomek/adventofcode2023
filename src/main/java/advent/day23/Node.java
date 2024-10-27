package advent.day23;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Node {
    final int i, j;

    private List<Edge> edges = new ArrayList<>();

    public Node(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public String key() {
        String paddedI = String.format("%0" + 3 + "d", i);
        String paddedJ = String.format("%0" + 3 + "d", j);

        return paddedI + " " + paddedJ;
    }

    @Override
    public String toString() {
        return "{ " +
            "\"i\": " + i + ", " +
            "\"j\": " + j + ", " +
            "\"edges\": " + edges +
            " }";
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
        return i == node.i && j == node.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
