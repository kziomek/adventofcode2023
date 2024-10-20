package advent.day22;

public class Point {
    int x, y, z;

    public Point(String point) {
        String[] coordinates = point.split(",");
        x = Integer.parseInt(coordinates[0]);
        y = Integer.parseInt(coordinates[1]);
        z = Integer.parseInt(coordinates[2]);
    }

    public Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "[" +
             + x +
            ", " + y +
            ", " + z +
            ']';
    }
}
