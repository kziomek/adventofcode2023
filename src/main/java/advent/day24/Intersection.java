package advent.day24;

import java.math.BigDecimal;

public class Intersection {
    double x, y;

    public Intersection(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Intersection{" +
            "x=" + x +
            ", y=" + y +
            '}';
    }
}
