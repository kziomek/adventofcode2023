package advent.day17;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CityBlock {

    private int i, j;

    private int value;
    private int totalCost;
    private CityBlock prev;
    private boolean seen = false;
    private char fromDirection = '.';

    Map<Character, CityBlock> edges = new HashMap<>();

    public CityBlock(int i, int j, int value) {
        this.i = i;
        this.j = j;
        this.value = value;
//        this.totalCost = Integer.MAX_VALUE;
        this.totalCost = 999;
    }

    public String getKey() {
        return i + "_" + j;
    }

    public int getValue() {
        return value;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public CityBlock getPrev() {
        return prev;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public void addEdge(Character direction, CityBlock targetBlock) {
        edges.put(direction, targetBlock);
    }

    public Map<Character, CityBlock> getEdges() {
        return edges;
    }

    public void updateTotalCost(int sourceValue) {
        totalCost = sourceValue;
    }

    public void setSeen() {
        this.seen = true;
    }

    public boolean isSeen() {
        return seen;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public void setPrev(CityBlock block) {
        prev = block;
    }

    public void setFromDirection(char fromDirection) {
        this.fromDirection = fromDirection;
    }

    public char getFromDirection() {
        return fromDirection;
    }

}
