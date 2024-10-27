package advent.day23;

public class Edge {
    int length;
    Node target;

    public Edge(int length, Node target) {
        this.length = length;
        this.target = target;
    }

    @Override
    public String toString() {
        return "{ " +
            "\"length\": " + length + ", " +
            "\"target\": " + target +
            " }";
    }
}
