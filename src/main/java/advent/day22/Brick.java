package advent.day22;

public class Brick {
    Point a, b;

    public Brick(String[] points) {
        a = new Point(points[0]);
        b = new Point(points[1]);
    }

    public Brick(Point pointA, Point pointB) {
        this.a = pointA;
        this.b = pointB;
    }

    @Override
    public String toString() {
        return "{" +
            "a=" + a +
            ", b=" + b +
            '}';
    }
}
