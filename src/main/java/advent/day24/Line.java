package advent.day24;

import java.math.BigDecimal;

public class Line {

    Input input;
    BigDecimal a, b;

    public Line(BigDecimal a, BigDecimal b, Input input) {
        this.a = a;
        this.b = b;
        this.input = input;
    }

    @Override
    public String toString() {
        return "Line{" +
            "a=" + a +
            ", b=" + b +
            '}';
    }
}
