package advent.day5;

public class Position implements Comparable<Position> {
    long pos;
    boolean isStart;

    public Position(long pos, boolean isStart) {
        this.pos = pos;
        this.isStart = isStart;
    }

    @Override
    public int compareTo(Position other) {
        if (this.pos == other.pos) {
            return 0;
        }
        return this.pos > other.pos ? 1 : -1;
    }
}
