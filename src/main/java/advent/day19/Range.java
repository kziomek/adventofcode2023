package advent.day19;

import java.util.Objects;

public class Range {
    long from, to;

    public Range() {
        from = 1;
        to = 4000;
    }

    public Range(long from, long to) {
        this.from = from;
        this.to = to;
    }

    public Range clone() {
        return new Range(from, to);
    }

    public void update(long from, long to) {
        this.from = from;
        this.to = to;
    }

    public void subtract(Range range) {
        if (this.equals(range)) {
            return;
        }
        if (range.from > this.from) {
            this.from = range.from;
        }
        if (range.to < this.to) {
            this.to = range.to;
        }
        System.out.println("");
        //        if (from<= range.from) {
        //            from= range.from+1;
        //        }
        //        if (to)
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Range range = (Range) o;
        return from == range.from && to == range.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "Range{" +
            "from=" + from +
            ", to=" + to +
            '}';
    }

    public long getRangeValue() {
        return to - from + 1;
    }
}
