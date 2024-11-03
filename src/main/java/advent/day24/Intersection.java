package advent.day24;

import java.math.BigDecimal;

public class Intersection {
    BigDecimal x, y;

    public Intersection(BigDecimal x, BigDecimal y) {
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
