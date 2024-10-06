package advent.day18;

import java.util.Objects;

public class Line {
    long x, a, b;

    public Line(long x, long a, long b) {
        this.x = x;
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Line line = (Line) o;
        return x == line.x && a == line.a && b == line.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, a, b);
    }

    @Override
    public String toString() {
        return "Line{" +
            "x=" + x + ", [" + a + "][" + b + ']';
    }
}
