package advent.day24;

public class Input {
    long px, py, pz, vx, vy, vz;

    public Input(String[] input) {
        this.px = Long.parseLong(input[0]);
        this.py = Long.parseLong(input[1]);
        this.pz = Long.parseLong(input[2]);
        this.vx = Long.parseLong(input[3]);
        this.vy = Long.parseLong(input[4]);
        this.vz = Long.parseLong(input[5]);
    }

    public Input(long px, long py, long pz, long vx, long vy, long vz) {
        this.px = px;
        this.py = py;
        this.pz = pz;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
    }

    double a() {
        return (double) vy / vx;
    }

    double b() {
        return py - a() * px;
    }

    @Override
    public String toString() {
        return "Input{" +
            "px=" + px +
            ", py=" + py +
            ", pz=" + pz +
            ", vx=" + vx +
            ", vy=" + vy +
            ", vz=" + vz +
            ", a=" + a() +
            ", b=" + b() +
            '}';
    }
}
