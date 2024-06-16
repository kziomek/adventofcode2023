package advent.day5;

public class Slate implements Comparable<Slate> {
    long start, end;

    public Slate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public int compareTo(Slate other) {
        return (int) ((this.start - other.start) / Math.abs(this.start - other.start));
    }
}
