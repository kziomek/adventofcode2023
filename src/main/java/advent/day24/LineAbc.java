package advent.day24;

public class LineAbc {
    long a, b, c;

    public LineAbc(long px, long py, long vx, long vy, int vrx, int vry) {
        a = -1 * (vy - vry);
        b = vx - vrx;
        c = -1 * (a * px + b * py);
    }

    public boolean pointOnLine(long x, long y) {
        return a * x + b * y + c == 0;
    }
}
