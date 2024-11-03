package advent.day24;

import java.math.BigDecimal;

public class LineAbc {
    BigDecimal a, b, c;

    public LineAbc(long px, long py, long vx, long vy, int vrx, int vry) {
        a = BigDecimal.valueOf(-1 * (vy - vry));
        b = BigDecimal.valueOf(vx - vrx);

        c = (a.multiply(BigDecimal.valueOf(px)).add(b.multiply(BigDecimal.valueOf(py)))).multiply(BigDecimal.valueOf(-1));
    }

//    public boolean pointOnLine(long x, long y) {
//        return a * x + b * y + c == 0;
//    }
}
