package advent.day18;

import java.util.Objects;

public class Line {
//    int x, a, b  ;

    Point a, b;

    public Line(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return a + "->" + b;
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
        return Objects.equals(a, line.a) && Objects.equals(b, line.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}
