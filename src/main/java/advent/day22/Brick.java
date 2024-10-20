package advent.day22;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Brick brick = (Brick) o;
        return Objects.equals(a, brick.a) && Objects.equals(b, brick.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    @Override
    public String toString() {
        return "{" +
            "a=" + a +
            ", b=" + b +
            '}';
    }

    public boolean intersectsXY(Brick brick) {
        boolean isYFixed = this.a.y == this.b.y && this.a.y == brick.a.y && this.a.y == brick.b.y;
        boolean isXFixed = this.a.x == this.b.x && this.a.x == brick.a.x && this.a.x == brick.b.x;

        return (isYFixed && intersectsX(brick)) // parallel y
            || (isXFixed && intersectsY(brick)) // parallel x
            || intersectPerpendicular(brick)
            ;
    }

    private boolean intersectPerpendicular(Brick brick) {
        return (this.a.x == this.b.x
            && this.a.y <= brick.a.y && this.b.y >= brick.a.y
            && this.a.x >= brick.a.x && this.a.x <= brick.b.x)
            || (this.a.y == this.b.y
            && this.a.x <= brick.a.x && this.b.x >= brick.a.x
            && this.a.y >= brick.a.y && this.a.y <= brick.b.y);
    }

    private boolean intersectsX(Brick brick) {
        return (this.a.x >= brick.a.x && this.a.x <= brick.b.x) || (this.b.x >= brick.a.x && this.b.x <= brick.b.x)
            || (this.a.x >= brick.a.x && this.b.x <= brick.b.x) || (this.a.x <= brick.a.x && this.b.x >= brick.b.x);
    }

    private boolean intersectsY(Brick brick) {
        return (this.a.y >= brick.a.y && this.a.y <= brick.b.y) || (this.b.y >= brick.a.y && this.b.y <= brick.b.y)
            || (this.a.y >= brick.a.y && this.b.y <= brick.b.y) || (this.a.y <= brick.a.y && this.b.y >= brick.b.y);
    }
}
