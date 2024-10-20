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
        // Checks if both bricks are horizontally aligned (fixed y) or vertically aligned (fixed x)
        boolean isYFixed = this.a.y == this.b.y && this.a.y == brick.a.y && this.a.y == brick.b.y;
        boolean isXFixed = this.a.x == this.b.x && this.a.x == brick.a.x && this.a.x == brick.b.x;

        // If aligned horizontally or vertically, check respective intersections in that axis
        return (isYFixed && intersectsX(brick)) // parallel in y-axis
            || (isXFixed && intersectsY(brick)) // parallel in x-axis
            || intersectPerpendicular(brick);   // perpendicular case
    }

    private boolean intersectPerpendicular(Brick brick) {
        // This handles cases where one brick is vertical and the other horizontal
        // Vertical brick intersects with horizontal brick
        return (this.a.x == this.b.x // This brick is vertical
            && brick.a.y == brick.b.y // The other brick is horizontal
            && this.a.y <= brick.a.y && this.b.y >= brick.a.y // Vertical range overlaps horizontal y
            && this.a.x >= brick.a.x && this.a.x <= brick.b.x) // Horizontal range covers vertical x
            || (this.a.y == this.b.y // This brick is horizontal
            && brick.a.x == brick.b.x // The other brick is vertical
            && this.a.x <= brick.a.x && this.b.x >= brick.a.x // Horizontal range overlaps vertical x
            && this.a.y >= brick.a.y && this.a.y <= brick.b.y); // Vertical range covers horizontal y
    }

    private boolean intersectsX(Brick brick) {
        // Check for horizontal overlap (x-axis)
        return (this.a.x >= brick.a.x && this.a.x <= brick.b.x) // This start inside brick
            || (this.b.x >= brick.a.x && this.b.x <= brick.b.x) // This end inside brick
            || (this.a.x <= brick.a.x && this.b.x >= brick.b.x); // This fully covers brick
    }

    private boolean intersectsY(Brick brick) {
        // Check for vertical overlap (y-axis)
        return (this.a.y >= brick.a.y && this.a.y <= brick.b.y) // This start inside brick
            || (this.b.y >= brick.a.y && this.b.y <= brick.b.y) // This end inside brick
            || (this.a.y <= brick.a.y && this.b.y >= brick.b.y); // This fully covers brick
    }

    public boolean intersectsZ(Brick brick) {
        // Check for vertical overlap (z-axis)
        return (this.a.z >= brick.a.z && this.a.z <= brick.b.z) // This start inside brick
            || (this.b.z >= brick.a.z && this.b.z <= brick.b.z) // This end inside brick
            || (this.a.z <= brick.a.z && this.b.z >= brick.b.z); // This fully covers brick
    }
}
