package advent.day24;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class NeverTellMeTheOdds {

    //    static final long MIN = 7L;
    //    static final long MAX = 25L;

    static final long MIN = 200000000000000L;
    static final long MAX = 400000000000000L;

    public static void main(String[] args) throws IOException {

        //        List<Input> lines = Parser.parse("src/main/resources/day24/example.txt");
        List<Input> lines = Parser.parse("src/main/resources/day24/my-input.txt");

        long counter = 0;
        for (int i = 0; i < lines.size() - 1; i++) {
            for (int j = i + 1; j < lines.size(); j++) {
                Input line1 = lines.get(i);
                Input line2 = lines.get(j);
                System.out.println(line1);
                System.out.println(line2);

                if (isParallel(line1, line2)) {
                    System.out.println("Hailstones' paths are parallel; they never intersect.");
                    System.out.println();
                    continue;
                }

                Intersection intersection = intersect(line1, line2);
                System.out.println(intersection);

                if (isInCrossArea(intersection)) {
                    System.out.println("Is in crossed area");
                    if (isFutureIntersection(intersection, line1) && isFutureIntersection(intersection, line2)) {
                        System.out.println("Is future intersection");
                        counter++;
                    }
                }
                System.out.println();
            }
            System.out.println("Result " + counter);
        }
    }

    private static Intersection intersect(Input line1, Input line2) {
        double x = (line2.b() - line1.b()) / (line1.a() - line2.a());
        double y1 = line1.a() * x + line1.b();
        double y2 = line2.a() * x + line2.b();
        return new Intersection(x, y1);
    }

    private static boolean isParallel(Input line1, Input line2) {
        return line1.a() == line2.a();
    }

    private static boolean isInCrossArea(Intersection intersection) {
        return (intersection.x >= MIN && intersection.x <= MAX
            && intersection.y >= MIN && intersection.y <= MAX);
    }

    private static boolean isFutureIntersection(Intersection intersection, Input line) {
        return (intersection.x - line.px) / line.vx >= 0;
    }
}
