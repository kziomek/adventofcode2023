package advent.day5.part2;

public class Range {

    long start;
    long end;

    public Range(long start, long end) {
        this.start = start;
        this.end = end;
    }

    /*
         |--|
        |----|
     */
    public boolean within(Range other) {
        return start >= other.start && end <= other.end;
    }

    /*
           |---|
      |---|
    */
    public boolean overlappingRight(Range other) {
        return start >= other.start && end > other.end;
    }

    /*
    |---|
        |---|
    */
    public boolean overlappingLeft(Range other) {
        return start < other.start && end <= other.end;
    }


    public boolean disjoint(Range other) {
        return end < other.start || start> other.end;
    }
}
