package advent.day18;

import java.util.ArrayList;
import java.util.List;

public class PickAndShoelaceTheorem {

    /**
     * https://www.youtube.com/watch?v=2Rk2-NxmgR4&ab_channel=0xdf
     * https://en.wikipedia.org/wiki/Pick%27s_theorem
     * https://en.wikipedia.org/wiki/Shoelace_formula
     * A = i + b/2 - 1
     */
    public static long calculateArea(List<Step> steps) {

        List<Point> points = collectPoints(steps);
        System.out.println(points);

        long perimeter = calculatePerimeter(points);
        System.out.println("Perimeter " + perimeter);

        long internalArea = calculateInternalArea(points);
        System.out.println("Internal area " + internalArea);

        //  A = i + b/2 - 1 - This gives as area
        //  i = A - b/2 +1
        //  i + b = A + b/2 +2

        return internalArea + (perimeter / 2) + 1;
    }

    private static List<Point> collectPoints(List<Step> steps) {
        List<Point> points = new ArrayList<>();

        int x = 0, y = 0;
        points.add(new Point(x, y));

        for (Step step : steps) {
            switch (step.getDirection()) {
                case 'R' -> {
                    x += step.getLength();
                    points.add(new Point(x, y));
                }
                case 'L' -> {
                    x -= step.getLength();
                    points.add(new Point(x, y));
                }
                case 'U' -> {
                    y -= step.getLength();
                    points.add(new Point(x, y));
                }
                case 'D' -> {
                    y += step.getLength();
                    points.add(new Point(x, y));
                }
            }
        }
        return points;
    }

    public static long calculatePerimeter(List<Point> points) {
        long perimeter = 0;
        for (int i = 0; i < points.size(); i++) {
            Point a = points.get(i);
            Point b = points.get((i + 1) % points.size());
            perimeter += Math.abs(b.x - a.x) + Math.abs(b.y - a.y);
        }
        return perimeter;
    }

    public static long calculateInternalArea(List<Point> points) {
        long internalArea = 0;
        for (int i = 0; i < points.size(); i++) {
            Point a = points.get(i);
            Point b = points.get((i + 1) % points.size());

            internalArea += (a.y + b.y) * (a.x - b.x);
        }

        return Math.abs(internalArea / 2);
    }
}
