package advent.day24;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NeverTellMeTheOdds {

    //    static final long MIN = 7L;
    //    static final long MAX = 25L;

    static final long MIN = 200000000000000L;
    static final long MAX = 400000000000000L;

    private static final int N = 400;

    // TODO collect result with example, then check with my input
    public static void main(String[] args) throws IOException {
        System.out.println(187016878804004L + 175507140888229L + 177831791810924L); // this numbers are found at intersection of 5 first lines from following calculation

        //        List<Input> lines = Parser.parse("src/main/resources/day24/example.txt");
        //                                                        List<Input> lines = Parser.parse("src/main/resources/day24/my-input.txt");
        List<Input> lines = Parser.parse("src/main/resources/day24/my-input-short.txt");

        for (Input line : lines) {

            System.out.println(line);
        }

                /*
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
        }*/

        // part 2
        //        part2b(lines);
        part2c(lines);
    }

    private static void part2c(List<Input> lines) {

        System.out.println("XY");
        for (int vrx = -1 * N; vrx < N; vrx++) {
            for (int vry = -1 * N; vry < N; vry++) {

                Set<String> intersections = new HashSet<>();
                int counter = 0;
                int matchCounter = 0;
                for (int i = 0; i < lines.size(); i++) {
                    for (int j = i + 1; j < lines.size(); j++) {
                        counter++;
                        Input line0 = lines.get(i);
                        Input line1 = lines.get(j);

                        LineAbc pxy0 = new LineAbc(line0.px, line0.py, line0.vx, line0.vy, vrx, vry);
                        LineAbc pxy1 = new LineAbc(line1.px, line1.py, line1.vx, line1.vy, vrx, vry);
                        Intersection intersectXY = intersect(pxy0, pxy1);
                        if (intersectXY != null) {
                            matchCounter++;
                            String sIntersection = "Intersected x=" + intersectXY.x + " y=" + intersectXY.y;
                            intersections.add(sIntersection);
                            //                            System.out.println(intersectXY);
                        }
                    }
                }

                if (intersections.size() == 1) {
                    System.out.println("Intersected all lines with matchCounter " + matchCounter + "/" + counter + "  here " + intersections.stream().findFirst().get());
                }
            }
        }

        System.out.println("XZ");
        for (int vrx = -1 * N; vrx < N; vrx++) {
            for (int vrz = -1 * N; vrz < N; vrz++) {

                Set<String> intersections = new HashSet<>();
                int counter = 0;
                int matchCounter = 0;
                for (int i = 0; i < lines.size(); i++) {
                    for (int j = i + 1; j < lines.size(); j++) {
                        counter++;
                        Input line0 = lines.get(i);
                        Input line1 = lines.get(j);

                        LineAbc pxz0 = new LineAbc(line0.px, line0.pz, line0.vx, line0.vz, vrx, vrz);
                        LineAbc pxz1 = new LineAbc(line1.px, line1.pz, line1.vx, line1.vz, vrx, vrz);
                        Intersection intersectXZ = intersect(pxz0, pxz1);
                        if (intersectXZ != null) {
                            matchCounter++;
                            String sIntersection = "Intersected x=" + intersectXZ.x + " z=" + intersectXZ.y;
                            intersections.add(sIntersection);
                            //                            System.out.println(intersectXZ);
                        }
                    }
                }

                if (intersections.size() == 1) {
                    System.out.println("Intersected all lines with matchCounter " + matchCounter + "/" + counter + "  here " + intersections.stream().findFirst().get());
                }
            }
        }

        System.out.println("YZ");
        for (int vry = -1 * N; vry < N; vry++) {
            for (int vrz = -1 * N; vrz < N; vrz++) {

                Set<String> intersections = new HashSet<>();
                int counter = 0;
                int matchCounter = 0;
                for (int i = 0; i < lines.size(); i++) {
                    for (int j = i + 1; j < lines.size(); j++) {
                        counter++;
                        Input line0 = lines.get(i);
                        Input line1 = lines.get(j);

                        LineAbc pyz0 = new LineAbc(line0.py, line0.pz, line0.vy, line0.vz, vry, vrz);
                        LineAbc pyz1 = new LineAbc(line1.py, line1.pz, line1.vy, line1.vz, vry, vrz);
                        Intersection intersectYZ = intersect(pyz0, pyz1);
                        if (intersectYZ != null) {
                            matchCounter++;
                            String sIntersection = "Intersected x=" + intersectYZ.x + " z=" + intersectYZ.y;
                            intersections.add(sIntersection);
                            //                            System.out.println(intersectYZ);
                        }
                    }
                }

                if (intersections.size() == 1) {
                    System.out.println("Intersected all lines with matchCounter " + matchCounter + "/" + counter + "  here " + intersections.stream().findFirst().get());
                }
            }
        }
    }

    private static void part2(List<Input> lines) {
        Input line0 = lines.get(0);
        Input line1 = lines.get(1);
        Map<Integer, Map<Character, Long>> map = new HashMap<>();
        //        Map<Integer, Long> map = new HashMap<>();

        for (int v = -1 * N; v < N; v++) {
            for (int t = 0; t < N; t++) {
                long xr = line0.px + t * (line0.vx - v);
                if (validateLinesX(lines, v, xr)) {
                    System.out.println("xr=" + xr + " vx=" + v + " t=" + t);
                    System.out.println();
                    map.computeIfAbsent(t, k -> new HashMap<>());
                    map.get(t).put('x', xr);
                }
            }
        }

        for (int v = -1 * N; v < N; v++) {
            for (int t = 1; t < N; t++) {
                long yr = line0.py + t * (line0.vy - v);
                if (validateLineY(lines, v, yr)) {
                    System.out.println("yr=" + yr + " vy=" + v + " t=" + t);
                    System.out.println();
                    map.computeIfAbsent(t, k -> new HashMap<>());
                    map.get(t).put('y', yr);
                }
            }
        }
        //
        for (int v = -1 * N; v < N; v++) {
            for (int t = 1; t < N; t++) {
                long zr = line0.pz + t * (line0.vz - v);
                if (validateLineZ(lines, v, zr)) {
                    System.out.println("zr=" + zr + " vz=" + v + " t=" + t);
                    System.out.println();
                    map.computeIfAbsent(t, k -> new HashMap<>());
                    map.get(t).put('z', zr);
                }
            }
        }

        System.out.println(map.values().stream().filter(c -> c.size() == 3).toList());
    }

    private static boolean validateLinesX(List<Input> lines, int v, long ox) {
        for (int i = 1; i < lines.size(); i++) {
            if (!validX(lines.get(i), v, ox)) {
                return false;
            }
        }

        return true;
    }

    private static boolean validX(Input line, int vr, long xr) {
        BigDecimal divide;
        try {
            divide = BigDecimal.valueOf(xr - line.px).divide(BigDecimal.valueOf((line.vx - vr)), 3, RoundingMode.HALF_UP);
        } catch (ArithmeticException ex) {
            return false;
        }

        if (divide.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        //                System.out.println("valid t " + divide);
        //TODO Test for positive t
        return divide.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0;

        //        double t = (double) (xr - line.px) / (line.vx - vr);
        //                System.out.println("valid t " + t);
        //        return t % 1 == 0;
    }

    private static boolean validateLineY(List<Input> lines, int v, long oy) {
        for (int i = 1; i < lines.size(); i++) {
            if (!validY(lines.get(i), v, oy)) {
                return false;
            }
        }

        return true;
    }

    private static boolean validY(Input line, int vr, long yr) {
        BigDecimal divide;
        try {
            divide = BigDecimal.valueOf(yr - line.py).divide(BigDecimal.valueOf((line.vy - vr)), 3, RoundingMode.HALF_UP);
        } catch (ArithmeticException ex) {
            return false;
        }
        //        System.out.println("valid t " + divide);
        if (divide.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        return divide.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0;

        //        double t = (double) (yr - line.py) / (line.vy - vr);
        //        //        System.out.println("valid t " + t);
        //        return t % 1 == 0;
    }

    private static boolean validateLineZ(List<Input> lines, int v, long oz) {
        for (int i = 1; i < lines.size(); i++) {
            if (!validZ(lines.get(i), v, oz)) {
                return false;
            }
        }
        return true;
    }

    private static boolean validZ(Input line, int vr, long zr) {
        BigDecimal divide;
        try {
            divide = BigDecimal.valueOf(zr - line.pz).divide(BigDecimal.valueOf((line.vz - vr)), 3, RoundingMode.HALF_UP);
        } catch (ArithmeticException ex) {
            return false;
        }

        if (divide.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        return divide.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0;

        //        double t = (double) (zr - line.pz) / (line.vz - vr);
        //        //        System.out.println("valid t " + t);
        //        return t % 1 == 0;
    }

    //    private static Intersection intersect(Input line1, Input line2) {
    //        double x = (line2.b() - line1.b()) / (line1.a() - line2.a());
    //        double y1 = line1.a() * x + line1.b();
    //        double y2 = line2.a() * x + line2.b();
    //        return new Intersection(x, y1);
    //    }
    //
    //    private static Intersection intersect(ProjectedInput line1, ProjectedInput line2) {
    //        double x = (line2.b() - line1.b()) / (line1.a() - line2.a());
    //        double y1 = line1.a() * x + line1.b();
    //        double y2 = line2.a() * x + line2.b();
    //        return new Intersection(x, y1);
    //    }

    private static Intersection intersect(LineAbc line1, LineAbc line2) {
        BigDecimal d = line2.b.multiply(line1.a).subtract(line1.b.multiply(line2.a));

        //        long d = line2.b * line1.a - line1.b * line2.a;
        if (d.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }

        BigDecimal x = line1.b.multiply(line2.c).subtract(line2.b.multiply(line1.c)).divide(d, 3, RoundingMode.HALF_UP);
        BigDecimal y = line1.c.multiply(line2.a).subtract(line2.c.multiply(line1.a)).divide(d, 3, RoundingMode.HALF_UP);

        return new Intersection(x, y);
    }

    //    private static Intersection intersect(LineAbc line1, LineAbc line2) {
    //        long d = line2.b * line1.a - line1.b * line2.a;
    //        if (d == 0) {
    //            return null;
    //        }
    //        double x = (double) (line1.b * line2.c - line2.b * line1.c) / d;
    //        double y = (double) (line1.c * line2.a - line2.c * line1.a) / d;
    //        return new Intersection(x, y);
    //    }

    private static boolean isParallel(Input line1, Input line2) {
        return line1.a() == line2.a();
    }

    //    private static boolean isInCrossArea(Intersection intersection) {
    //        return (intersection.x >= MIN && intersection.x <= MAX
    //            && intersection.y >= MIN && intersection.y <= MAX);
    //    }

    //    private static boolean isFutureIntersection(Intersection intersection, Input line) {
    //        return (intersection.x - line.px) / line.vx >= 0;
    //    }
}
