package advent.day19;

import java.util.Map;

public class Edge {
    Node to;

    Map<Character, Range> ranges;

    public Edge( Node to, Map<Character, Range> ranges) {
        this.to = to;
        this.ranges = ranges;
    }
}
