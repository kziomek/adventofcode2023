package advent.day24;

public class ProjectedInput {
    long px, py, vx, vy;

    public ProjectedInput(long px, long py, long vx, long vy, int vrx, int vry) {
        this.px = px;
        this.py = py;
        this.vx = vx - vrx;
        this.vy = vy - vry;
    }
    double a() {
        return (double) vy / vx;
    }

    double b() {
        return py - a() * px;
    }
}
