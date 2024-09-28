package advent.day17;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CityBlock {
    Set<DirectionBlock> directionBlockSet = new HashSet<>();

    private final int i, j;

    private final int value;

    Map<Character, CityBlock> edges = new HashMap<>();

    public CityBlock(int i, int j, int value) {
        this.i = i;
        this.j = j;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void addEdge(Character direction, CityBlock targetBlock) {
        edges.put(direction, targetBlock);
    }

    public Map<Character, CityBlock> getEdges() {
        return edges;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
