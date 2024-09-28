package advent.day17;

import java.util.Objects;

public class DirectionBlock {

    private int totalCost;
    private final char entryDirection;
    private final CityBlock original;
    private final int straightCounter;
    private DirectionBlock prev;

    public DirectionBlock(CityBlock original, char entryDirection, int straightCounter) {
        this.original = original;
        this.entryDirection = entryDirection;
        this.straightCounter = straightCounter;
        this.totalCost = 999;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public CityBlock getOriginal() {
        return original;
    }

    public char getEntryDirection() {
        return entryDirection;
    }

    public int getStraightCounter() {
        return straightCounter;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DirectionBlock that = (DirectionBlock) o;
        return entryDirection == that.entryDirection && original.getI() == that.original.getI() && original.getJ() == that.original.getJ() && that.straightCounter == straightCounter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entryDirection, original.getI(), original.getJ(), straightCounter);
    }

    public void setPrev(DirectionBlock prev) {
        this.prev = prev;
    }

    public DirectionBlock getPrev() {
        return prev;
    }

    public void print() {
        System.out.println( "["+original.getI() + "][" + original.getJ() + "] " + entryDirection + " " + totalCost + " " + straightCounter);
    }
}
