package advent.day24;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class NeverTellMeTheOdds {

//    static final BigDecimal MIN = new BigDecimal(7);
    static final BigDecimal MIN = new BigDecimal(200000000000000L);
//    static final BigDecimal MAX = new BigDecimal(25);
    static final BigDecimal MAX = new BigDecimal(400000000000000L);

    public static void main(String[] args) throws IOException {

//        List<Input> inputs = Parser.parse("src/main/resources/day24/example.txt");
        List<Input> inputs = Parser.parse("src/main/resources/day24/my-input.txt");
        List<Line> lines = toLines(inputs);

        List<Intersection> validIntersections = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            for (int j = i + 1; j < lines.size(); j++) {
                Line line1 = lines.get(i);
                Line line2 = lines.get(j);
                Intersection intersection = intersect(line1, line2); //todo implement never instersect
                System.out.println(intersection);
                if (intersection != null && isFutureIntersection(intersection, line1) && isFutureIntersection(intersection, line2) && isInCrossArea(intersection)) {
                    System.out.println("Is future crossing intersection");
                    System.out.println();
                    validIntersections.add(intersection);
                }
                ; // && insideArea
            }
        }
        System.out.println("Result " + validIntersections.size());

        //        intersect(input.get(2), input.get(3));

    }

    private static boolean isInCrossArea(Intersection intersection) {
        return (intersection.x.compareTo(MIN) >= 0 && intersection.x.compareTo(MAX) <= 0
            && intersection.y.compareTo(MIN) >= 0 && intersection.y.compareTo(MAX) <= 0);
    }

    private static boolean isFutureIntersection(Intersection intersection, Line line1) {
        //        isIntersectionInsideArea()
        boolean isDecreasing = line1.input.vx <= 0;
        boolean isIncreasing = line1.input.vx >= 0;

        if (intersection.x.compareTo(new BigDecimal(line1.input.px)) <= 0 && isDecreasing) {
            return true;
        }
        if (intersection.x.compareTo(new BigDecimal(line1.input.px)) >= 0 && isIncreasing) {
            return true;
        }

        return false;
    }

    private static List<Line> toLines(List<Input> inputs) {
        return inputs.stream().map(NeverTellMeTheOdds::toLine).toList();
    }

    //    private static Intersection intersect(Line line1, Line line2) {
    //        System.out.println(line1);
    //        System.out.println(line2);
    //        // x1 y1 x2 y2
    //
    //        Intersection intersection = intersect(line1, line2);
    //        System.out.println(intersection);
    //
    //    }

    private static Intersection intersect(Line line1, Line line2) {
        if (line1.a.compareTo(line2.a) == 0) {
            System.out.println("parallel lines");
            return null;
        }
        BigDecimal x = (line2.b.subtract(line1.b)).divide(line1.a.subtract(line2.a), 3, RoundingMode.HALF_UP);
        BigDecimal y1 = line1.a.multiply(x).add(line1.b);
        BigDecimal y2 = line2.a.multiply(x).add(line2.b);
        if (y1.compareTo(y2) != 0) {
            System.out.println("not exact intersection math y1 " + y1 + " y2 " + y2);
            //            throw new IllegalStateException("wrong intersection math");
        }
        return new Intersection(x, y1);
    }

    private static Line toLine(Input input) {
        long x1 = input.px;
        long y1 = input.py;
        long x2 = input.px + input.vx;
        long y2 = input.py + input.vy;

        BigDecimal a = BigDecimal.valueOf(y1 - y2).divide(BigDecimal.valueOf((x1 - x2)), 3, RoundingMode.HALF_UP);
        //        double a = (double) (y1 - y2) / (x1 - x2);

        BigDecimal b = BigDecimal.valueOf(y1).subtract(a.multiply(BigDecimal.valueOf(x1)));

        return new Line(a, b, input);
    }
}
