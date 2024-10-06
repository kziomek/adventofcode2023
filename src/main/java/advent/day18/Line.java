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

    //    Point a, b;
    //
    //    public Line(Point a, Point b) {
    //        this.a = a;
    //        this.b = b;
    //    }

    //    @Override
    //    public String toString() {
    //        return a + "->" + b;
    //    }
    //
    //    @Override
    //    public boolean equals(Object o) {
    //        if (this == o) {
    //            return true;
    //        }
    //        if (o == null || getClass() != o.getClass()) {
    //            return false;
    //        }
    //        Line line = (Line) o;
    //        return Objects.equals(a, line.a) && Objects.equals(b, line.b);
    //    }
    //
    //    @Override
    //    public int hashCode() {
    //        return Objects.hash(a, b);
    //    }
}
